import re
import sys

# Regular definitions
anyChar = r"[ -~]"
letter = r"[a-zA-Z]"
digit = r"[0-9]"
whitespace = r"[ \t]"
eol = r"\n"

# Ordered by "precedence"
tokens = {
    r"main": "main",
    r"if": "if",
    r"else": "else",
    r"while": "while",
    r"return": "return",
    r"print": "print",
    r",": ",",
    r";": ";",
    r"\(": "(",
    r"\)": ")",
    r"\{": "{",
    r"\}": "}",
    r"\[": "[",
    r"\]": "]",
    r"bool|char|float|int": "type",
    r"true|false": "boolLiteral",
    r"==|!=": "equOp",
    r"<=|>=|<|>": "relOp",
    r"=": "assignOp",
    r"\+|\-": "addOp",
    rf"//(?:{anyChar}|{whitespace})*{eol}": "comment",
    r"\*|/": "multOp",
    rf"'{anyChar}'": "charLiteral",
    rf"{letter}({letter}|{digit})*": "id",
    rf"{digit}+\.{digit}+": "floatLiteral",
    rf"{digit}+": "intLiteral",
}
patterns = {tokens[k]: k for k in tokens}

splitters = [
    "equOp",
    "relOp",
    "assignOp",
    "addOp",
    "comment",
    "multOp",
    "floatLiteral",
    "charLiteral",
]
splitting_regex = re.compile(
    "(" + "|".join([patterns[k] for k in splitters] + [r"\W"]) + ")"
)


def get_file_content(file):
    with open(file, "r") as f:
        return f.read()


def main(file):
    code = get_file_content(file)
    lexemes = splitting_regex.split(code)
    with open("output.txt", "w") as output:
        for lexeme in lexemes:
            for pattern, token_name in tokens.items():
                if re.fullmatch(pattern, lexeme):
                    print(f"{token_name}\t{lexeme}", file=output)
                    break


if __name__ == "__main__":
    main(sys.argv[1])
