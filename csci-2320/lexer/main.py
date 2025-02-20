import re
import sys

# Regular definitions
anyChar = r"[ -~]"
letter = r"[a-zA-Z]"
digit = r"[0-9]"
whitespace = r"[ \t]"
eol = r"\n"

# Ordered by "precedence" when tokenizing
token_patterns = [
    # anyChar|whitespace group is not capturing to avoid having it appear in
    # the lexemes list
    ("comment", rf"//(?:{anyChar}|{whitespace})*{eol}"),
    ("main", r"main"),
    ("if", r"if"),
    ("else", r"else"),
    ("while", r"while"),
    ("return", r"return"),
    ("print", r"print"),
    (",", r","),
    (";", r";"),
    ("(", r"\("),
    (")", r"\)"),
    ("{", r"\{"),
    ("}", r"\}"),
    ("[", r"\["),
    ("]", r"\]"),
    ("type", r"bool|char|float|int"),
    ("boolLiteral", r"true|false"),
    ("equOp", r"==|!="),
    ("relOp", r"<=|>=|<|>"),
    ("assignOp", r"="),
    ("addOp", r"\+|\-"),
    ("multOp", r"\*|/"),
    ("charLiteral", rf"'{anyChar}'"),
    # Another group that should not be capturing
    ("id", rf"{letter}(?:{letter}|{digit})*"),
    ("floatLiteral", rf"{digit}+\.{digit}+"),
    ("intLiteral", rf"{digit}+"),
]

# Although the below regex includes all tokens of Clite, not all of them need
# to be splitting to successfully split
splitting_regex = re.compile(
    f"({'|'.join([pattern for _, pattern in token_patterns])})"
)


def get_file_content(file: str) -> str:
    with open(file, "r") as f:
        return f.read()


def write_output(
    token_lexemes: list[tuple[str, str]], file: str = "output.txt"
) -> None:
    with open(file, "w") as output:
        for token, lexeme in token_lexemes:
            output.write(f"{token}\t{lexeme}\n")


def tokenize_lexemes(lexemes: list[str]) -> list[tuple[str, str]]:
    tokens = []
    for lexeme in lexemes:
        for token, pattern in token_patterns:
            # Lexeme is the token that matches first
            if re.fullmatch(pattern, lexeme):
                tokens.append((token, lexeme))
                break

    return tokens


def main(file: str) -> None:
    code = get_file_content(file)
    lexemes = splitting_regex.split(code)
    token_lexemes = tokenize_lexemes(lexemes)
    write_output(token_lexemes)


if __name__ == "__main__":
    main(sys.argv[1])
