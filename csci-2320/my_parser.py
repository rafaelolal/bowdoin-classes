# CSCI 2320: RD and more specifically LL(1) parser
# Author: Mohammad T. Irfan
# Edited by: Rafael Almeida '27

# Grammar
# Assignment -> Identifier = Expression
# Expression -> Term {(+|-) Term}
# Term -> Exponent {(*|/) Exponent}
# Exponent -> Factor [^ Exponent]
# Factor -> [-] Primary
# Primary -> Identifier|IntLiteral|(Expression)

# input
# space separated for the sake of easy lexing
i = "IntLiteral"
d = "Identifier"
my_input = f"{d} = - {i} - ( {i} * {d} ) ^ {i} ^ - {i} + ( {d} + {i} )"
print(f"Input: {my_input}")

# Lexing
tokens = my_input.split()

token_pointer = 0


def main():
    # start symbol
    assignment()
    # could not consume the whole input
    if token_pointer < len(tokens):
        error(
            f'Incomplete expression caused by token "{tokens[token_pointer]}" at index {str(token_pointer)}'
        )
    else:
        print("Valid Expression!")


# Assignment -> Identifier = Expression
def assignment():
    global token_pointer
    identifier()
    if token_pointer < len(tokens) and tokens[token_pointer] == "=":
        token_pointer += 1
        expression()

    else:
        error(f'Missing "=" at index {str(token_pointer)}')


# Expression -> Term {(+|-) Term}
def expression():
    global token_pointer
    term()
    while token_pointer < len(tokens) and (
        tokens[token_pointer] == "+" or tokens[token_pointer] == "-"
    ):
        token_pointer += 1
        term()


# Term -> Exponent {(*|/) Exponent}
def term():
    global token_pointer
    exponent()
    while token_pointer < len(tokens) and (
        tokens[token_pointer] == "*" or tokens[token_pointer] == "/"
    ):
        token_pointer += 1
        exponent()


# Exponent -> Factor [^ Exponent]
def exponent():
    global token_pointer
    factor()
    if token_pointer < len(tokens) and tokens[token_pointer] == "^":
        token_pointer += 1
        exponent()

    # no need for else since the exponent is optional


# Factor -> [-] Primary
def factor():
    global token_pointer
    if token_pointer < len(tokens) and tokens[token_pointer] == "-":
        token_pointer += 1

    primary()


# Primary -> Identifier|IntLiteral|(Expression)
def primary():
    global token_pointer
    if token_pointer < len(tokens) and tokens[token_pointer] in [
        "Identifier",
        "IntLiteral",
    ]:
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


def identifier():
    global token_pointer
    if token_pointer < len(tokens) and tokens[token_pointer] == "Identifier":
        token_pointer += 1
    else:
        error(f'Missing "Identifier" at index {str(token_pointer)}')


def error(msg):
    print(msg)
    exit()


if __name__ == "__main__":
    main()
