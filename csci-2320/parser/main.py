import sys

from grammar import program
from my_parser import Parser

SAMPLE_INPUT_PATH = "sample_input.txt"


def main(file_path: str) -> None:
    token_lexemes = read_file(file_path).split("\n")
    TOKEN = 0
    # get tokens only from token_lexemes
    tokens = [pair.split("\t")[TOKEN] for pair in token_lexemes]
    parser = Parser(tokens)
    parser.parse(program)
    print("Success")


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
