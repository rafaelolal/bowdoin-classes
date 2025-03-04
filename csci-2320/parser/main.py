import sys

from grammar import program
from my_parser import Parser

SAMPLE_INPUT_PATH = "sample_input.txt"


def main(file_path: str) -> None:
    token_lexemes = read_file(file_path).split("\n")
    # get tokens only from token_lexemes
    tokens = split_tokens_from_lexemes(token_lexemes)
    tokens = preprocess_tokens(tokens)
    parser = Parser(tokens)
    parser.parse(program)
    print("Success")


def split_tokens_from_lexemes(token_lexemes: list[str]) -> list[str]:
    TOKEN = 0
    return [pair.split("\t")[TOKEN] for pair in token_lexemes]


def preprocess_tokens(tokens: list[str]) -> list[str]:
    return [token for token in tokens if is_token_valid(token)]


def is_token_valid(token: str) -> bool:
    not_valid_tokens = ["comment"]
    return token not in not_valid_tokens


def read_file(file_path):
    with open(file_path, "r") as file:
        return file.read()


if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python main.py <file_path>")
        print(f'Running sample input "{SAMPLE_INPUT_PATH}"')
        main(SAMPLE_INPUT_PATH)

    else:
        main(sys.argv[1])
