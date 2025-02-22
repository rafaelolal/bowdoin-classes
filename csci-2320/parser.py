# CSCI 2320: RD and more specifically LL(1) parser
# Author: Mohammad T. Irfan
# Edited by: Rafael Almeida '27

# Grammar
# Expression -> Product {(+|-) Product}
# Product -> Exponent {(*|/) Exponent}
# Exponent -> Term [^ Exponent]
# Term -> 0|1|2|3|4|5|6|7|8|9|(Expression)

# input
# space separated for the sake of easy lexing
i = "intLiteral"
my_input = f"{i} + ( {i} * {i} ) ^ {i} ^ {i} + ( {i} + {i} )"
print(f"Input: {my_input}")

# Lexing
tokens = my_input.split()

token_pointer = 0


def main():
    # start symbol
    expression()
    # could not consume the whole input
    if token_pointer < len(tokens):
        error(
            f'Incomplete expression caused by token "{tokens[token_pointer]}" at index {str(token_pointer)}'
        )
    else:
        print("Valid Expression!")


# Expression -> Product {(+|-) Product}
def expression():
    global token_pointer
    product()
    while token_pointer < len(tokens) and (
        tokens[token_pointer] == "+" or tokens[token_pointer] == "-"
    ):
        token_pointer += 1
        product()


# Product -> Exponent {(*|/) Exponent}
def product():
    global token_pointer
    exponent()
    while token_pointer < len(tokens) and (
        tokens[token_pointer] == "*" or tokens[token_pointer] == "/"
    ):
        token_pointer += 1
        exponent()


# Exponent -> Term [^ Exponent]
def exponent():
    global token_pointer
    term()
    if token_pointer < len(tokens) and tokens[token_pointer] == "^":
        token_pointer += 1
        exponent()

    # no need for else since the exponent is optional


# Term -> intLiteral|(Expression)
def term():
    global token_pointer
    if token_pointer < len(tokens) and tokens[token_pointer] == "intLiteral":
        token_pointer += 1
    elif token_pointer < len(tokens) and tokens[token_pointer] == "(":
        token_pointer += 1
        expression()
        if token_pointer < len(tokens) and tokens[token_pointer] == ")":
            token_pointer += 1
        else:
            error(f'Missing ")" at index {str(token_pointer)}')

    else:
        error(
            f'Invalid token "{tokens[token_pointer]}" at index {str(token_pointer)}'
        )


def error(msg):
    print(msg)
    exit()


if __name__ == "__main__":
    main()
