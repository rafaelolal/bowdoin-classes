##############################################################################
# Design Choices:
##############################################################################
# Implicit promotion from int to float during arithmetic operations
# Implicit promotion during assignment (int can be assigned to float)
# No implicit demotion (float cannot be assigned to int without explicit
#   casting)
# Result type of arithmetic operations depends on operand types (float if any
#   operand is float) or if the result became a float (e.g 3/2 = 1.5 would be
#   a float)
# Arithmetic operators (+, -, *, /, %) require numeric operands (int or float)
# Comparison operators (<, <=, >, >=) require numeric operands and produce bool
# Equality operators (==, !=) require matching types and produce bool
# Logical operators (&&, ||, !) require bool operands and produce bool
# Runtime checks for division by zero

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
from typing import Any, Union

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
            split_line = line.split("\t")
            if len(split_line) != 2:
                continue

            token, lexeme = split_line
            token = token.strip()
            lexeme = lexeme.strip()

            # Ignore comments.
            if token == "comment":
                continue

            token_lexemes.append((token, lexeme))

        return token_lexemes


class Interpreter:
    def __init__(self, token_lexemes: list[tuple[str, str]]):
        self.symbols: list[dict[str, tuple[str, Any]]] = [
            {}
        ]  # Start with global scope
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

    def declarations(self, execute: bool) -> None:
        while self.can_consume(FIRST["declaration"]):
            self.declaration(execute)

    def declaration(self, execute: bool) -> None:
        var_type = self.consume()  # type
        var_name = self.consume()  # id
        if execute:
            self.declare_variable(var_type, var_name)

        while self.can_consume(","):
            self.consume()  # comma
            var_name = self.consume()  # id

            if execute:
                self.declare_variable(var_type, var_name)

        self.consume()  # semi_colon

    def statements(self, execute: bool) -> None:
        while self.can_consume(FIRST["statement"]):
            self.statement(execute)

    def statement(self, execute: bool) -> None:
        if self.can_consume(FIRST["block"]):
            self.block(execute)
        elif self.can_consume(FIRST["assignment"]):
            self.assignment(execute)
        elif self.can_consume(FIRST["print_statement"]):
            self.print_statement(execute)
        elif self.can_consume(FIRST["if_statement"]):
            self.if_statement(execute)
        elif self.can_consume(FIRST["while_statement"]):
            self.while_statement(execute)
        elif self.can_consume(FIRST["return_statement"]):
            self.return_statement(execute)

    def block(self, execute: bool) -> None:
        self.open_brace()
        self.declarations(execute)
        self.statements(execute)
        self.close_brace()

    def print_statement(self, execute: bool) -> None:
        self.consume()  # print
        output, _ = self.expression(execute)
        if execute:
            print(output)
        self.consume()  # semi_colon

    def if_statement(self, execute: bool) -> None:
        self.consume()  # if
        self.consume()  # open_parenthesis
        condition_value, _ = self.expression(execute)
        self.consume()  # close_parenthesis

        # Execute if block only if the outer context is executed and condition is true
        self.statement(execute and condition_value)

        if self.can_consume("else"):
            self.consume()  # else
            # Execute else block only if the outer context is executed and condition is false
            self.statement(execute and not condition_value)

    def while_statement(self, execute: bool) -> None:
        self.consume()  # while
        self.consume()  # open_parenthesis

        condition_start = self.index
        condition_value, _ = self.expression(execute)
        self.consume()  # close_parenthesis

        # Save statement position
        statement_start = self.index

        # First execute the statement if needed
        self.statement(execute and condition_value)

        # Continue looping while condition is true
        while execute and condition_value:
            # Re-evaluate the condition
            self.index = condition_start
            condition_value, _ = self.expression(execute)
            # Potentially the statement again
            self.index = statement_start
            self.statement(condition_value)

    def return_statement(self, execute: bool) -> None:
        self.consume()  # return
        self.expression(execute)
        self.consume()  # semi_colon

        if execute:
            sys.exit(0)

    def assignment(self, execute: bool) -> None:
        var_name = self.consume()  # id
        self.consume()  # assign_op
        result_value, result_type = self.expression(execute)
        self.consume()  # semi_colon

        if not execute:
            return

        if not self.is_variable_declared(var_name):
            raise VariableNotDeclaredError(var_name)

        var_type, _ = self.get_variable_bindings(var_name)

        # Type checking for assignment
        if not self.is_compatible_assignment(var_type, result_type):
            raise AssignmentTypeError(
                var_name, var_type, result_type, result_value
            )

        self.update_variable(var_name, result_value)

    def is_compatible_assignment(
        self, target_type: str, value_type: str
    ) -> bool:
        # Allow int to float conversion, otherwise require exact type match
        return (
            target_type == "float" and value_type == "int"
        ) or target_type == value_type

    def expression(self, execute: bool) -> tuple[Any, str]:
        output, output_type = self.conjunction(execute)
        while self.can_consume(FIRST["or_op"]):
            op = self.consume()  # or_op
            # Short circuit "or" by only evaluating the next term if the
            # current one is false
            right, right_type = self.conjunction(not output and execute)

            if not (not output and execute):
                continue

            # Type checking for logical operations
            if output_type != "bool" or right_type != "bool":
                raise BinaryOperatorTypeError(op, output_type, right_type)

            output = output or right

        return output, output_type

    def conjunction(self, execute: bool) -> tuple[Any, str]:
        output, output_type = self.equality(execute)
        while self.can_consume(FIRST["and_op"]):
            op = self.consume()  # and_op
            # Short circuit "and" by only evaluating the next term if the
            # current one is true
            right, right_type = self.equality(output and execute)

            if not (output and execute):
                continue

            # Type checking for logical operations
            if output_type != "bool" or right_type != "bool":
                raise BinaryOperatorTypeError(op, output_type, right_type)

            output = output and right

        return output, output_type

    def equality(self, execute: bool) -> tuple[Any, str]:
        output, output_type = self.relation(execute)
        if self.can_consume(FIRST["equ_op"]):
            op = self.consume()  # equ_op
            right, right_type = self.relation(execute)

            if not execute:
                return output, output_type

            # Type checking for equality operations
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

    def relation(self, execute: bool) -> tuple[Any, str]:
        output, output_type = self.addition(execute)
        if self.can_consume(FIRST["rel_op"]):
            op = self.consume()  # rel_op
            right, right_type = self.addition(execute)

            if not execute:
                return output, output_type

            # Type checking for relational operations
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

    def addition(self, execute: bool) -> tuple[Any, str]:
        output, output_type = self.term(execute)
        while self.can_consume(FIRST["add_op"]):
            op = self.consume()  # add_op
            right, right_type = self.term(execute)

            if not execute:
                continue

            # Type checking for arithmetic operations
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

            # Promote to float if either operand is float
            if output_type == "float" or right_type == "float":
                output_type = "float"

        return output, output_type

    def term(self, execute: bool) -> tuple[Any, str]:
        output, output_type = self.factor(execute)
        while self.can_consume(FIRST["mult_op"]):
            op = self.consume()  # mult_op
            right, right_type = self.factor(execute)

            if not execute:
                continue

            # Type checking for arithmetic operations
            if not (
                output_type in ("int", "float")
                and right_type in ("int", "float")
            ):
                raise BinaryOperatorTypeError(op, output_type, right_type)

            if op == "*":
                output = output * right

            elif op == "/":
                if right == 0:
                    raise ZeroDivisionError("Division by zero")

                output = output / right

            elif op == "%":
                if right == 0:
                    raise ZeroDivisionError("Modulo by zero")

                output = output % right

            else:
                raise ShouldNotReachHereError()

            # Promote to float if either operand is float or output is float
            if (
                int(output) != output
                or output_type == "float"
                or right_type == "float"
            ):
                output_type = "float"

        return output, output_type

    def factor(self, execute: bool) -> tuple[Any, str]:
        op = None
        if self.can_consume(FIRST["unary_op"]):
            op = self.consume()  # unary_op

        output, output_type = self.primary(execute)

        if not execute:
            return output, output_type

        if op == "-":
            if output_type not in ("int", "float"):
                raise UnaryOperatorTypeError(op, output_type)

            output = -output

        elif op == "+":
            if output_type not in ("int", "float"):
                raise UnaryOperatorTypeError(op, output_type)
            # Unary plus is a no-op

        elif op == "!":
            if output_type != "bool":
                raise UnaryOperatorTypeError(op, output_type)

            output = not output

        # Unary operator not present
        elif op is None:
            pass

        else:
            raise ShouldNotReachHereError()

        return output, output_type

    def primary(self, execute: bool) -> tuple[Any, str]:
        # Not checking anything related to types here because the input is said to be syntactically valid.
        if self.can_consume("id"):
            var_name = self.consume()

            # If this statement is not to be executed, it does not matter
            # what I return
            if not execute:
                return None, "undefined"

            if not self.is_variable_declared(var_name):
                raise VariableNotDeclaredError(var_name)

            var_type, var_value = self.get_variable_bindings(var_name)

            # Check if variable was initialized
            if var_value is None:
                raise UninitializedVariableError(var_name)

            return var_value, var_type

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
            return self.parenthesis_expression(execute)

        else:
            print(
                "my current index",
                self.index,
                "my current token",
                self.get_current_token(),
            )
            raise ShouldNotReachHereError()

    def parenthesis_expression(self, execute: bool) -> tuple[Any, str]:
        self.consume()  # open_parenthesis
        result = self.expression(execute)
        self.consume()  # close_parenthesis
        return result

    def open_brace(self) -> str:
        self.symbols.append({})
        return self.consume()

    def close_brace(self) -> str:
        self.symbols.pop()
        return self.consume()

    def declare_variable(self, var_type: str, var_name: str) -> None:
        # Check if variable is in top scope only
        if var_name in self.symbols[-1]:
            raise VariableDeclaredError(var_name)

        self.symbols[-1][var_name] = (var_type, None)

    def is_variable_declared(self, var_name: str) -> bool:
        # Check if variable exists in current or any parent scope
        for scope in reversed(self.symbols):
            if var_name in scope:
                return True

        return False

    def get_variable_bindings(
        self, var_name: str
    ) -> tuple[str, Union[str, None]]:
        # Get variable type from the innermost scope that contains it
        for scope in reversed(self.symbols):
            if var_name in scope:
                return scope[var_name][TYPE], scope[var_name][VALUE]

        raise VariableNotDeclaredError(var_name)

    def update_variable(self, var_name: str, value: Any) -> None:
        # Update variable in the innermost scope that contains it
        for scope in reversed(self.symbols):
            if var_name in scope:
                var_type = scope[var_name][TYPE]
                scope[var_name] = (var_type, value)
                return

        raise VariableNotDeclaredError(var_name)

    def get_current_token(self) -> str:
        return self.token_lexemes[self.index][TOKEN]

    def can_consume(self, token: str | set[str]) -> bool:
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
            f'Operator "{op}" not supported between "{left_type}" and "{right_type}".'
        )


class VariableDeclaredError(Exception):
    def __init__(self, var_name: str):
        super().__init__(f'Variable "{var_name}" already declared.')


class VariableNotDeclaredError(Exception):
    def __init__(self, var_name: str):
        super().__init__(f'Variable "{var_name}" not declared.')


class AssignmentTypeError(Exception):
    def __init__(
        self, var_name: str, var_type: str, value_type: str, value: Any
    ):
        super().__init__(
            f'Cannot assign "{value_type}" "{value}" to "{var_name}" of type "{var_type}".'
        )


class UnaryOperatorTypeError(Exception):
    def __init__(self, op: str, var_type: str):
        super().__init__(
            f'Type mismatch: operator "{op}" not supported for "{var_type}".'
        )


class UninitializedVariableError(Exception):
    def __init__(self, var_name: str):
        super().__init__(f'Variable "{var_name}" used before initialization.')


class ShouldNotReachHereError(Exception):
    def __init__(self, message: str = "Should not reach here"):
        super().__init__(message)


FIRST = {
    "declaration": {"type"},
    "statement": {"{", "id", "print", "if", "while", "return"},
    "block": {"{"},
    "assignment": {"id"},
    "print_statement": {"print"},
    "if_statement": {"if"},
    "while_statement": {"while"},
    "return_statement": {"return"},
    # Note: added orOp and andOp to work regardless of how the tokens are
    # defined in the input
    "or_op": {"||", "orOp"},
    "and_op": {"&&", "andOp"},
    "equ_op": {"equOp"},
    "rel_op": {"relOp"},
    "add_op": {"addOp"},
    "mult_op": {"multOp"},
    # Note: added unaryOp to work regardless of how the tokens are defined
    "unary_op": {"-", "+", "!", "unaryOp"},
}

if __name__ == "__main__":
    if len(sys.argv) < 2:
        sys.argv.append("t17.txt")
        print("Usage: python main.py <input_file>")
        print("Using default input file: sample_input_1.txt")

    Main.main(sys.argv[1])
