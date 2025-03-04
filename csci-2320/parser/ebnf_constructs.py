from typing import Callable, Iterator, Union

from my_parser import Parser


def option(
    *elements: tuple,
) -> Callable[[Parser], Iterator[Union[Callable, str]]]:
    """
    Creates an option (...|...) construct for a parser.

    Returns:
        A function that yields the first element that can be consumed, if any.

    Example:
        S -> "if" | "while" | "for" becomes option("if", "while", "for"),
        which would create a construct that tries to match exactly one of the
        tokens "if", "while", or "for".

    Note:
        The returned function has as additional attributes the original
        elements passed and what type of EBNF construct it is. This is used
        for calculating first sets.
    """

    def f(parser: Parser) -> Iterator[Union[Callable, str]]:
        # try to consume one of the elements
        for element in elements:
            # cannot consume the element
            if parser.token() not in parser.first(element):
                continue

            yield element
            # only yield the first element that can be consumed
            return

    # used to calculate the first set
    f.original_args = elements
    f.ebnf_type = "option"
    return f


def optional(
    *elements: tuple,
) -> Callable[[Parser], Iterator[Union[Callable, str]]]:
    """
    Creates an optional [...] construct for a parser.

    Returns:
        A function that yields the elements if the first element can be consumed.

    Example:
        S -> ["if" expression] becomes optional("if", expression), which
        would create a construct that tries to match the token "if" followed
        by an expression at most once.
    """

    def f(parser) -> Iterator[Union[Callable, str]]:
        first_element = elements[0]
        # cannot begin the optional sequence
        if parser.token() not in parser.first(first_element):
            return

        # if it consumes one of the args, it consumes all of them
        for element in elements:
            yield element

    f.original_args = elements
    f.ebnf_type = "optional"
    return f


def repetition(
    *elements: tuple,
) -> Callable[[Parser], Iterator[Union[Callable, str]]]:
    """
    Creates a zero-or-more {...} construct for a parser.

    Returns:
        A function that repeatedly yields the elements as long as the first
        element can be consumed.

    Example:
        S -> {expression} becomes repetition(expression), which would create
        a construct that tries to match an expression zero or more times.
    """

    def f(parser) -> Iterator[Union[Callable, str]]:
        # consume as many tokens as possible
        while True:
            first_element = elements[0]
            # cannot begin the repetition sequence
            if parser.token() not in parser.first(first_element):
                return

            # if it consumes one of the args, it consumes all of them
            for element in elements:
                yield element

    f.original_args = elements
    f.ebnf_type = "repetition"
    return f
