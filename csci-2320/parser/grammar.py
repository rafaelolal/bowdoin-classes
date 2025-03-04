from typing import Callable

from ebnf_constructs import (
    option,
    optional,
    repetition,
)


# Program -> type main ( ) { Declarations Statements }
def program() -> tuple[Callable]:
    return (
        type_,
        main_,
        open_parenthesis,
        close_parenthesis,
        open_brace,
        declarations,
        statements,
        close_brace,
    )


# I decided to create productions for each non-terminal symbol to avoid
# repeating the same string in multiple places
# main -> "main"
def main_() -> tuple[str]:
    return ("main",)


# main -> "main"s
def open_parenthesis() -> tuple[str]:
    return ("(",)


# closeParenthesis -> ")"
def close_parenthesis() -> tuple[str]:
    return (")",)


# openBrace -> "{"
def open_brace() -> tuple[str]:
    return ("{",)


# closeBrace -> "}"
def close_brace() -> tuple[str]:
    return ("}",)


# type -> "int" | "float" | "bool" | "char"
def type_() -> tuple[str]:
    return ("type",)


# Declarations -> { Declaration }
def declarations() -> tuple[Callable]:
    return (repetition(declaration),)


# Declaration -> type id { "," id } ";"
def declaration() -> tuple[Callable]:
    return (
        type_,
        id_,
        repetition(comma, id_),
        semi_colon,
    )


# comma -> ","
def comma() -> tuple[str]:
    return (",",)


# semiColon -> ";"
def semi_colon() -> tuple[str]:
    return (";",)


# Statements -> { Statement }
def statements() -> tuple[Callable]:
    return (repetition(statement),)


# Statement -> Block | Assignment | PrintStmt | IfStatement | WhileStmt | ReturnStmt
def statement() -> tuple[Callable]:
    return (
        option(
            block,
            assignment,
            print_stmt,
            if_stmt,
            while_stmt,
            return_stmt,
        ),
    )


# Block -> "{" Statements "}"
def block() -> tuple[Callable]:
    return (
        open_brace,
        statements,
        close_brace,
    )


# PrintStmt -> "print" Expression ;
def print_stmt() -> tuple[Callable]:
    return (
        print_,
        expression,
        semi_colon,
    )


# print -> "print"
def print_() -> tuple[str]:
    return ("print",)


# IfStatement -> "if" "(" Expression ")" Statement [ "else" Statement ]
def if_stmt() -> tuple[Callable]:
    return (
        if_,
        open_parenthesis,
        expression,
        close_parenthesis,
        statement,
        optional(else_, statement),
    )


# if -> "if"
def if_() -> tuple[str]:
    return ("if",)


# else -> "else"
def else_() -> tuple[str]:
    return ("else",)


# WhileStmt -> "while" "("" Expression ")" Statement
def while_stmt() -> tuple[Callable]:
    return (
        while_,
        open_parenthesis,
        expression,
        close_parenthesis,
        statement,
    )


# while -> "while"
def while_() -> tuple[str]:
    return ("while",)


# ReturnStmt -> return Expression ";"
def return_stmt() -> tuple[Callable]:
    return (
        return_,
        expression,
        semi_colon,
    )


# return -> "return"
def return_() -> tuple[str]:
    return ("return",)


# Assignment -> id assignOp Expression ";"
def assignment() -> tuple[Callable]:
    return (
        id_,
        assign_op,
        expression,
        semi_colon,
    )


# id -> letter { letter | digit }
def id_() -> tuple[str]:
    return ("id",)


# assignOp -> "="
def assign_op() -> tuple[str]:
    return ("assignOp",)


# Expression -> Conjunction { "||" Conjunction }
def expression() -> tuple[Callable]:
    return (
        conjunction,
        repetition(or_op, conjunction),
    )


# orOp -> "||"
def or_op() -> tuple[str]:
    return ("||",)


# ParenthesisExpression -> "(" Expression ")"
def parenthesis_expression() -> tuple[Callable]:
    return (open_parenthesis, expression, close_parenthesis)


# Conjunction -> Equality { "&&" Equality }
def conjunction() -> tuple[Callable]:
    return (
        equality,
        repetition(and_op, equality),
    )


# andOp -> "&&"
def and_op() -> tuple[str]:
    return ("&&",)


# Equality -> Relation [ equOp Relation ]
def equality() -> tuple[Callable]:
    return (
        relation,
        optional(equ_op, relation),
    )


# equOp -> "==" | "!="
def equ_op() -> tuple[str]:
    return ("equOp",)


# Relation -> Addition [ relOp Addition ]
def relation() -> tuple[Callable]:
    return (
        addition,
        optional(rel_op, addition),
    )


# relOp -> "<" | "<=" | ">" | ">="
def rel_op() -> tuple[str]:
    return ("relOp",)


# Addition -> Term { addOp Term }
def addition() -> tuple[Callable]:
    return (
        term,
        repetition(add_op, term),
    )


# addOp -> "+" | "-"
def add_op() -> tuple[str]:
    return ("addOp",)


# Term -> Factor { multOp Factor }
def term() -> tuple[Callable]:
    return (
        factor,
        repetition(mult_op, factor),
    )


# multOp -> "*" | "/"
def mult_op() -> tuple[str]:
    return ("multOp",)


# Factor -> [ UnaryOp ] Primary
def factor() -> tuple[Callable]:
    return (primary,)


# Primary -> id | intLiteral | boolLiteral | floatLiteral | charLiteral | "("" Expression ")"
def primary() -> tuple[Callable]:
    return (
        option(
            id_,
            intLiteral,
            boolLiteral,
            floatLiteral,
            charLiteral,
            parenthesis_expression,
        ),
    )


# intLiteral -> digit { digit }
def intLiteral() -> tuple[str]:
    return ("intLiteral",)


# boolLiteral -> true | false
def boolLiteral() -> tuple[str]:
    return ("boolLiteral",)


# floatLiteral -> digit { digit } "." digit { digit }
def floatLiteral() -> tuple[str]:
    return ("floatLiteral",)


# charLiteral -> "'" anyChar "'"
def charLiteral() -> tuple[str]:
    return ("charLiteral",)


# comment -> "//" { anyChar }
def comment() -> tuple[str]:
    return ("comment",)
