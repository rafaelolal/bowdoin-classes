# file name is my_parser.py to avoid conflict with the built-in parser module

from functools import cache
from typing import Any, Callable, Union


class Parser:
    def __init__(self, tokens: list[str]) -> None:
        self.tokens = tokens
        self.index = 0

    def parse(self, symbol: Union[Callable[[], list], str]) -> None:
        """
        Parses the current token stream based on the given grammar symbol.

        Raises:
                Exits the program if tokens in the input do not match the
                expected grammar.

        Notes:
            - If the symbol is a terminal, the function tries to consume the
            current token.
            - If the symbol is a non-terminal, the function recursively parses
            the right-hand side of the symbol.
            - Special handling is provided for EBNF constructs.
        """

        # symbol is terminal
        if isinstance(symbol, str):
            self.consume(symbol)
            return

        # symbol is non-terminal
        right_hand_side = symbol()
        for component in right_hand_side:
            # component is an option, optional, or repetition construct
            if self.is_ebnf_construct(component):
                for element in component(self):
                    self.parse(element)

            # component is non-terminal or terminal
            else:
                self.parse(component)

    @cache
    def first(self, symbol: Union[Callable[[], list], str]) -> tuple[set]:
        """
        Calculate the FIRST set for a given symbol in the grammar.

        Notes:
            - For terminals, the FIRST set is just the terminal itself.
            - For non-terminals, the function recursively computes the FIRST set
            by examining each component of its right-hand side.
            - Special handling is provided for EBNF constructs like options,
            optionals, and repetition.
            - Results are cached for better performance.
        """

        if self.is_terminal(symbol):
            return {symbol}

        # symbol is a non-terminal
        first_set = set()
        right_hand_side = symbol()
        for component in right_hand_side:
            # component is a terminal or non-terminal
            if not self.is_ebnf_construct(component):
                first_set |= self.first(component)
                # stop here because the first set does not include the
                # subsequent component, i.e. this component not nullable
                return first_set

            # component is an option
            elif self.is_ebnf_construct(component, "option"):
                # get the first set of all option elements
                for element in component.original_args:
                    first_set |= self.first(element)

                # stop here because none of the options in the grammar are nullable
                return first_set

            # component is an optional or repetition
            else:
                first_element = component.original_args[0]
                first_set |= self.first(first_element)
                # do not return because we need to include the first set
                # of the subsequent component, i.e. this component is nullable

        return first_set

    def consume(self, expected_token) -> None:
        """
        Tries to consume the next token if it matches the expected token.
        """

        if self.index == len(self.tokens):
            self.error(f'Expected "{expected_token}" but reached EOF')

        if self.token() != expected_token:
            self.error(
                f'Expected "{expected_token}" at index {self.index} but got "{self.token()}"'
            )

        self.index += 1

    def token(self) -> str:
        """
        The current token being parsed.
        """

        return self.tokens[self.index]

    def is_ebnf_construct(self, component: Any, construct: str = None) -> bool:
        if not hasattr(component, "ebnf_type"):
            return False

        if construct is None:
            return True

        return component.ebnf_type == construct

    def is_terminal(self, symbol: Any) -> bool:
        """
        Determines if the provided symbol is a terminal symbol.
        """

        return isinstance(symbol, str)

    def error(self, message: str) -> None:
        """
        Prints a formatted syntax error message and exits the program.
        """

        print(f"Syntax Error: {message}")
        exit(1)
