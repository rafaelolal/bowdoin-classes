##############################################################################
# Grammar:
##############################################################################
# Program -> type main \( \) \{ Declarations Statements \}
# Declarations -> { Declaration }
# Declaration -> type id { , id} ;
# type -> int | bool | float | char
# Statements -> { Statement }
# Statement -> Block | Assignment | Printstatement | IfStatement | Whilestatement | Returnstatement
# Note: added declarations to the definition of block.
# Block -> \{ Declarations Statements \}
# Printstatement -> print Expression ;
# IfStatement -> if \( Expression \) Statement [ else Statement ]
# Whilestatement -> while \( Expression \) Statement
# Returnstatement -> return Expression ;
# Assignment -> id assignOp Expression ;
# assignOp -> =
# Expression -> Conjunction { orOp Conjunction }
# orOp -> ||
# Conjunction -> Equality { andOp Equality }
# andOp -> &&
# Equality -> Relation [ equOp Relation ]
# equOp -> == | !=
# Relation -> Addition [ relOp Addition ]
# relOp -> < | <= | > | >=
# Addition -> Term { addOp Term }
# addOp -> + | -
# Term -> Factor { multOp Factor }
# multOp -> * | / | %
# Factor -> [ UnaryOp ] Primary
# UnaryOp -> - | + | !
# Primary -> id | intLiteral | boolLiteral | floatLiteral | charLiteral | ParenthesisExpression
# ParenthesisExpression -> \( Expression \)
##############################################################################


import sys
from typing import Any

TOKEN = 0
LEXEME = 1
TYPE = 0
VALUE = 1
OUTPUT = 0
OUTPUT_TYPE = 1


class Main:
    @staticmethod
    def main(file: str) -> None:
        token_lexemes = Main.read_file(file)
        interpreter = Interpreter(token_lexemes)
        interpreter.program()

    @staticmethod
    def read_file(file: str) -> list[tuple[str, str]]:
        with open(file, "r") as f:
            lines = f.readlines()

        token_lexemes: list[tuple[str, str]] = []
        for line in lines:
            token, lexeme = line.split("\t")
            # Ignore comments.
            if token == "comment":
                continue

            token_lexemes.append((token.strip(), lexeme.strip()))

        return token_lexemes


class Interpreter:
    def __init__(self, token_lexemes: list[tuple[str, str]]):
        self.symbols: list[dict[str, tuple[str, Any]]] = []
        self.index = 0
        self.token_lexemes = token_lexemes

    def program(self) -> None:
        self.consume()  # type
        self.consume()  # main
        self.consume()  # open_parenthesis
        self.consume()  # close_parenthesis
        self.open_brace()
        self.declarations(True)
        self.statements(True)
        self.close_brace()

    def declarations(self, result: Any) -> None:
        while self.can_consume(FIRST["declaration"]):
            self.declaration(result)

    def declaration(self, result: Any) -> None:
        var_type = self.consume()  # type
        var_name = self.consume()  # id
        self.onion(var_type, var_name)
        while self.can_consume(","):
            self.consume()  # comma
            var_name = self.consume()
            if result:
                self.onion(var_type, var_name)

        self.consume()  # semi_colon

    def statements(self, result: Any) -> None:
        while self.can_consume(FIRST["statement"]):
            self.statement(result)

    def statement(self, result: Any) -> None:
        if self.can_consume(FIRST["block"]):
            self.block(result)
        elif self.can_consume(FIRST["assignment"]):
            self.assignment(result)
        elif self.can_consume(FIRST["print_statement"]):
            self.print_statement(result)
        elif self.can_consume(FIRST["if_statement"]):
            self.if_statement(result)
        elif self.can_consume(FIRST["while_statement"]):
            self.while_statement(result)
        elif self.can_consume(FIRST["return_statement"]):
            self.return_statement(result)
        else:
            raise ShouldNotReachHereError()

    def block(self, result: Any) -> None:
        self.open_brace()
        self.declarations(result)
        self.statements(result)
        self.close_brace()

    def print_statement(self, result: Any) -> None:
        self.consume()  # print
        output, _ = self.expression()
        if result:
            print(output)

        self.consume()  # semi_colon

    def if_statement(self, result: Any) -> None:
        self.consume()  # if
        self.consume()  # open_parenthesis
        output = self.expression()
        self.consume()  # close_parenthesis
        # This is "clever". It ensures that the statement is executed only
        # if this if was supposed to be executed and if the if expression
        # evaluated to true
        self.statement(result and output)

        if self.can_consume("else"):
            self.open_brace()
            self.statement(result and not output)
            self.close_brace()

    def while_statement(self, execute: Any) -> None:
        self.consume()  # while
        self.consume()  # open_parenthesis
        result = self.expression()
        self.consume()  # close_parenthesis
        start_index = self.index
        while execute and result:
            self.index = start_index
            result = self.expression()
            self.statement(execute and result)

    def return_statement(self, execute: Any) -> None:
        self.consume()  # return
        self.expression()
        self.consume()  # semi_colon
        if execute:
            sys.exit(0)

    def assignment(self, execute: Any) -> None:
        var_name = self.consume()  # id
        self.consume()  # assign_op
        result_value, result_type = self.expression()
        self.consume()  # semi_colon

        if var_name not in self.symbols[-1]:
            raise VariableNotDeclaredError(var_name)

        var_type = self.symbols[-1][var_name][TYPE]

        if (
            not (var_type == "float" and result_type == "int")
            and result_type != var_type
        ):
            raise AssiggnmentTypeError(var_name, var_type, result_type)

        if execute:
            self.symbols[-1][var_name] = (
                var_type,
                result_value,
            )

    def expression(self) -> tuple[Any, Any]:
        output, output_type = self.conjunction()
        while self.can_consume(FIRST["or_op"]):
            op = self.consume()  # or_op
            right, right_type = self.conjunction()
            if output_type != "bool" or right_type != "bool":
                raise BinaryOperatorTypeError(op, output_type, right_type)

            output = output or right

        return output, output_type

    def conjunction(self) -> tuple[Any, Any]:
        output, output_type = self.equality()
        while self.can_consume(FIRST["and_op"]):
            op = self.consume()  # and_op
            right, right_type = self.equality()
            if output_type != "bool" or right_type != "bool":
                raise BinaryOperatorTypeError(op, output_type, right_type)

            output = output and right

        return output, output_type

    def equality(self) -> tuple[Any, Any]:
        output, output_type = self.relation()
        if self.can_consume(FIRST["equ_op"]):
            op = self.consume()  # equ_op
            right, right_type = self.relation()
            if output_type != right_type:
                raise BinaryOperatorTypeError(op, output_type, right_type)

            if op == "==":
                output = output == right
            elif op == "!=":
                output = output != right
            else:
                raise ShouldNotReachHereError()

            output_type = "bool"

        return output, output_type

    def relation(self) -> tuple[Any, Any]:
        output, output_type = self.addition()
        if self.can_consume(FIRST["rel_op"]):
            op = self.consume()  # rel_op
            right, right_type = self.addition()
            if not (
                output_type in ("int", "float")
                and right_type in ("int", "float")
            ):
                raise BinaryOperatorTypeError(op, output_type, right_type)

            if op == "<":
                output = output < right
            elif op == "<=":
                output = output <= right
            elif op == ">":
                output = output > right
            elif op == ">=":
                output = output >= right
            else:
                raise ShouldNotReachHereError()

            output_type = "bool"

        return output, output_type

    def addition(self) -> tuple[Any, Any]:
        output, output_type = self.term()
        while self.can_consume(FIRST["add_op"]):
            op = self.consume()  # add_op
            right, right_type = self.term()
            if not (
                output_type in ("int", "float")
                and right_type in ("int", "float")
            ):
                raise BinaryOperatorTypeError(op, output_type, right_type)

            if op == "+":
                output = output + right
            elif op == "-":
                output = output - right
            else:
                raise ShouldNotReachHereError()

            if output_type == "float" or right_type == "float":
                output_type = "float"

        return output, output_type

    def term(self) -> tuple[Any, Any]:
        output, output_type = self.factor()
        while self.can_consume(FIRST["mult_op"]):
            op = self.consume()  # mult_op
            right, right_type = self.factor()
            if not (
                output_type in ("int", "float")
                and right_type in ("int", "float")
            ):
                raise BinaryOperatorTypeError(op, output_type, right_type)

            if op == "*":
                output = output * right
            elif op == "/":
                output = output / right
            elif op == "%":
                output = output % right
            else:
                raise ShouldNotReachHereError()

            if output_type == "float" or right_type == "float":
                output_type = "float"

        return output, output_type

    def factor(self) -> tuple[Any, Any]:
        if self.can_consume(FIRST["unary_op"]):
            op = self.consume()  # unary_op
            output, output_type = self.primary()

            if op == "-":
                if output_type not in ("int", "float"):
                    raise UnaryOperatorTypeError(op, output_type)

                output = -output

            elif op == "+":
                if output_type not in ("int", "float"):
                    raise UnaryOperatorTypeError(op, output_type)

                output = output

            elif op == "!":
                if output_type != "bool":
                    raise UnaryOperatorTypeError(op, output_type)

                output = not output
            else:
                raise ShouldNotReachHereError()

            return output, output_type

        return self.primary()

    def primary(self) -> tuple[Any, Any]:
        if self.can_consume("id"):
            var_name = self.consume()
            if var_name not in self.symbols[-1]:
                raise VariableNotDeclaredError(var_name)
            return (
                self.symbols[-1][var_name][VALUE],
                self.symbols[-1][var_name][TYPE],
            )

        elif self.can_consume("intLiteral"):
            return int(self.consume()), "int"
        elif self.can_consume("boolLiteral"):
            bool_val = self.consume()
            return bool_val == "true", "bool"
        elif self.can_consume("floatLiteral"):
            return float(self.consume()), "float"
        elif self.can_consume("charLiteral"):
            return self.consume(), "char"
        elif self.can_consume("("):
            result = self.parenthesis_expression()
            return result
        else:
            raise ShouldNotReachHereError()

    def parenthesis_expression(self) -> tuple[Any, Any]:
        self.consume()  # open_parenthesis
        result = self.expression()
        self.consume()  # close_parenthesis
        return result

    def open_brace(self) -> str:
        self.symbols.append({})
        return self.consume()

    def close_brace(self) -> str:
        self.symbols.pop()
        return self.consume()

    def onion(self, var_type: str, var_name: str) -> None:
        if var_name in self.symbols[-1]:
            raise VariableDeclaredError(var_name)

        self.symbols[-1][var_name] = (var_type, None)

    def can_consume(self, token: str | set[str]) -> bool:
        # No need to check if index is out of bounds because input is valid
        cur_token = self.token_lexemes[self.index][TOKEN]
        if isinstance(token, set):
            return cur_token in token

        return cur_token == token

    def consume(self) -> str:
        # No need to check if index is out of bounds because input is valid
        lexeme = self.token_lexemes[self.index][LEXEME]
        self.index += 1
        return lexeme


class BinaryOperatorTypeError(Exception):
    def __init__(self, op: str, left_type: str, right_type: str):
        super().__init__(
            f'Type mismatch: operator "{op}" not supported between "{left_type}" and "{right_type}".'
        )


class VariableDeclaredError(Exception):
    def __init__(self, var_name: str):
        super().__init__(f'Variable "{var_name}" already declared.')


class VariableNotDeclaredError(Exception):
    def __init__(self, var_name: str):
        super().__init__(f'Variable "{var_name}" not declared.')


class AssiggnmentTypeError(Exception):
    def __init__(self, var_name: str, var_type: str, value_type: str):
        super().__init__(
            f'Type mismatch: Cannot assign "{value_type}" to "{var_name}" of type "{var_type}".'
        )


class UnaryOperatorTypeError(Exception):
    def __init__(self, op: str, var_type: str):
        super().__init__(
            f'Type mismatch: operator "{op}" not supported for "{var_type}".'
        )


class ShouldNotReachHereError(Exception):
    def __init__(self):
        super().__init__("Should not reach here")


FIRST = {
    "declaration": {"type"},
    "statement": {"{", "id", "print", "if", "while", "return"},
    "block": {"{"},
    "assignment": {"id"},
    "print_statement": {"print"},
    "if_statement": {"if"},
    "while_statement": {"while"},
    "return_statement": {"return"},
    "or_op": {"||"},
    "and_op": {"&&"},
    "equ_op": {"equOp"},
    "rel_op": {"relOp"},
    "add_op": {"addOp"},
    "mult_op": {"multOp"},
    "unary_op": {"-", "+", "!"},
}

if __name__ == "__main__":
    # breakpoint()
    if len(sys.argv) < 2:
        sys.argv.append("sample_input_1.txt")
        print("Usage: python main.py <input_file>")
        print("Using default input file: sample_input_1.txt")

    Main.main(sys.argv[1])
