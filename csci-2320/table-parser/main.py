import re

TABLE = parsing_table = {
    "S": {"(": ["E", "$"], "id": ["E", "$"]},
    "E": {"(": ["T", "E'"], "id": ["T", "E'"]},
    "E'": {"$": [], "+": ["+", "T", "E'"], "-": ["-", "T", "E'"], ")": []},
    "T": {"(": ["F", "T'"], "id": ["F", "T'"]},
    "T'": {
        "$": [],
        "+": [],
        "-": [],
        "*": ["*", "F", "T'"],
        "/": ["/", "F", "T'"],
        ")": [],
    },
    "F": {"(": ["(", "E", ")"], "id": ["id"]},
}


def main(expression: str) -> None:
    tokens = get_tokens(expression)
    print("Valid" if parser(tokens) else "Invalid")


def get_tokens(epxression: str) -> list[str]:
    # Replace all numbers with id
    expression = re.sub(r"\d+", "id", epxression)
    # These are the supported operations and relevant tokens
    splitting_regex = re.compile(r"\+|\-|\*|/|\(|\)|id")
    # Using findall to avoid capturing groups and removing whitespace
    lexemes = splitting_regex.findall(expression)
    return lexemes


def parser(tokens: list[str]) -> bool:
    # Add the end of the input
    tokens.append("$")
    stack = ["$", "S"]
    i = 0
    while i < len(tokens):
        current = tokens[i]
        top = stack[-1]
        if top == current and top == "$":
            return True

        # top is terminal
        if is_terminal(top):
            if top == current:
                stack.pop()
                i += 1

            else:
                return False

        # top is non-terminal
        else:
            # Check if the top has a rule for the current token
            rhs = TABLE[top].get(current)
            if rhs is None:
                return False

            stack.pop()
            stack.extend(reversed(rhs))

    return False


def is_terminal(token: str) -> bool:
    return token not in TABLE


SAMPLE_1 = "10 + 20 * (5 + (2 + 3) - (10 / 2))"
SAMPLE_2 = "3 * (2 + (4 - (1 + (3 * 1)))"
SAMPLE_3 = ""

if __name__ == "__main__":
    main(SAMPLE_1)  # Valid
    main(SAMPLE_2)  # Invalid
    # Empty string is invalid because `S` expects to see a "(" or "id" at the start
    main(SAMPLE_3)  # Invalid
