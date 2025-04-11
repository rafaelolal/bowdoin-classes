"""
PLEASE READ THIS FOR EASIER GRADING

The relevant functions to check are:
    1. The main function
    2. The top of parse_and_interpret
    3. The body of parse_and_interpret.while_statement
    4. parse_and_interpret.expression
    5. parse_and_interpret.evaluate_expression
    6. parse_and_interpret.assignment
    7. parse_and_interpret.print
"""

import re
import sys
from typing import Any

DEBUG = not True
# Indices
TOKEN = 0
LEXEME = 1

INPUT_CODE = """
int x = 0;
while (x < 10) {
    x = x + 7;
}
print x;
"""


def main() -> None:
    lexemes = splitting_regex.split(INPUT_CODE)
    token_lexemes = tokenize_lexemes(lexemes)
    print("INPUT_CODE output:")
    success = parse_and_interpret(token_lexemes)
    print("END OF INPUT_CODE output\n")

    print(f"Successfully parsed and interpreted: {success}")


# GRAMMAR THAT I DEFINED FOR THE PURPOSE OF THIS FORMATIVE ASSIGNMENT 8 ONLY
# Program -> Declaration WhileStatement PrintStatement
# Declaration -> type id = intLiteral ;
# WhileStatement -> while \( Expression \) \{ Assignment \}
# PrintStatement -> print id ;
# Assignment -> id assignOp Expression ;
# Expression -> Conjunction { \|\| Conjunction }
# Conjunction -> Equality { && Equality }
# Equality -> Relation [ equOp Relation ]
# Relation -> Addition [ relOp Addition ]
# Addition -> Term { addOp Term }
# Term -> id | intLiteral | \( Expression \)
def parse_and_interpret(token_lexemes: list[tuple[str, str]]) -> bool:
    symbols: dict[str, Any] = {}
    # Using a mutable list with one int to avoid using the keyword global
    token: list[int] = [0]

    def while_statement() -> None:
        # start consuming while
        if can_consume("while"):
            token[0] += 1

        # consume expression
        expression_str = None
        if can_consume("("):
            token[0] += 1
            expression_str = expression()
            if can_consume(")"):
                token[0] += 1

        # start consuming body
        if can_consume("{"):
            token[0] += 1

        # while expression evalutes to True
        while_start = token[0]
        while expression_str is not None and evaluate_expression(
            expression_str
        ):
            # reset loop, redundant for first iteration
            token[0] = while_start
            # execute loop body
            assignment()

        # expression evaluated to false, so now I must consume the loop end
        if can_consume("}"):
            token[0] += 1

    def expression() -> str:
        expression_start = token[0]
        conjunction()
        while can_consume("||"):
            token[0] += 1
            conjunction()

        # When I am done consuming the expression,
        # I build a string of the whole expression to be used
        # by the evaluate_expression function which uses python.eval
        # To easily evaluate it
        expression_end = token[0]
        expression = token_lexemes[expression_start:expression_end]
        expression_str = "".join(
            expression[i][LEXEME] for i in range(len(expression))
        )
        return expression_str

    def evaluate_expression(expression: str) -> Any:
        # Cheating by using the python.eval function instead of
        # Getting the correct output of the eval by recursively computing it
        result = eval(
            expression,
            None,
            symbols,
        )
        return result

    def assignment() -> None:
        if can_consume("id"):
            token[0] += 1
            variable_name = token_lexemes[token[0] - 1][LEXEME]
            expression_str = None
            if can_consume("assignOp"):
                token[0] += 1
                expression_str = expression()
            if can_consume(";"):
                token[0] += 1
                if expression_str is not None:
                    symbols[variable_name] = evaluate_expression(
                        expression_str
                    )

    def print_statement() -> None:
        if can_consume("print"):
            token[0] += 1
            variable_name = None
            if can_consume("id"):
                token[0] += 1
                variable_name = token_lexemes[token[0] - 1][LEXEME]
            if can_consume(";"):
                token[0] += 1
                if variable_name is not None:
                    print(
                        f"{variable_name} = {symbols.get(variable_name, 'undefined')}"
                    )
                else:
                    print("Error: Invalid print syntax")
                    sys.exit(1)

    def declaration() -> None:
        if can_consume("type"):
            token[0] += 1
            variable_value = None
            variable_name = None
            if can_consume("id"):
                token[0] += 1
                variable_name = token_lexemes[token[0] - 1][LEXEME]
            if can_consume("assignOp"):
                token[0] += 1
                if can_consume("intLiteral"):
                    token[0] += 1
                    variable_value = int(token_lexemes[token[0] - 1][LEXEME])

            if can_consume(";"):
                token[0] += 1
                if variable_name is not None and variable_value is not None:
                    symbols[variable_name] = int(variable_value)
                else:
                    print("Error: Invalid declaration syntax")
                    sys.exit(1)

    ###################################################################
    # The following functions are not relevant and do not have any interesting
    # logic in them

    def program() -> None:
        declaration()
        while_statement()
        print_statement()

    def conjunction() -> None:
        equality()
        while can_consume("&&"):
            token[0] += 1
            equality()

    def equality() -> None:
        relation()
        if can_consume("equOp"):
            token[0] += 1
            relation()

    def relation() -> None:
        addition()
        if can_consume("relOp"):
            token[0] += 1
            addition()

    def addition() -> None:
        term()
        while can_consume("addOp"):
            token[0] += 1
            term()

    def term() -> None:
        if can_consume("id"):
            token[0] += 1
        elif can_consume("intLiteral"):
            token[0] += 1
        elif can_consume("("):
            token[0] += 1
            expression()
            if can_consume(")"):
                token[0] += 1

    def can_consume(token_: str) -> bool:
        return (
            token[0] < len(token_lexemes)
            and token_lexemes[token[0]][TOKEN] == token_
        )

    if DEBUG:
        breakpoint()

    # Beginning parsing and interpreting
    program()
    # This means all tokens were consumed and successfully parsed and interpreted
    return token[0] == len(token_lexemes)


#######################################################################
# ALL OF THE BELOW CODE WAS COPIED FROM SUMMATIVE ASSIGNMENT 1
# FOR LEXING PURPOSES WITH SOME MINOR CHANGES...

# Regular definitions
anyChar = r"[ -~]"
letter = r"[a-zA-Z]"
digit = r"[0-9]"
whitespace = r"[ \t]"
eol = r"\n"

# Ordered by "precedence" when tokenizing
token_patterns = [
    # added these to help debugging the parser project
    ("&&", r"&&"),
    ("||", r"\|\|"),
    # anyChar|whitespace group is not capturing to avoid having it appear in
    # the lexemes list
    ("while", r"while"),
    ("print", r"print"),
    (";", r";"),
    ("(", r"\("),
    (")", r"\)"),
    ("{", r"\{"),
    ("}", r"\}"),
    ("type", r"int"),
    ("equOp", r"==|!="),
    ("relOp", r"<=|>=|<|>"),
    ("assignOp", r"="),
    ("addOp", r"\+|\-"),
    # Another group that should not be capturing
    ("id", rf"{letter}(?:{letter}|{digit})*"),
    ("intLiteral", rf"{digit}+"),
]

splitting_regex = re.compile(
    f"({'|'.join([pattern for _, pattern in token_patterns])})"
)


def tokenize_lexemes(lexemes: list[str]) -> list[tuple[str, str]]:
    tokens: list[tuple[str, str]] = []
    for lexeme in lexemes:
        for token, pattern in token_patterns:
            if re.fullmatch(pattern, lexeme):
                tokens.append((token, lexeme))
                break

    return tokens


#######################################################################

if __name__ == "__main__":
    main()
