"""
Assignment 8: String Shuffling
Date: 04/13/2024
Author: Rafael Almeida
"""


def is_shuffle(a, b, c):
    """
    A shuffle of two strings A and B is formed by interspersing
    the characters into a new string, keeping the characters from
    A and B in the same order.

    Returns:
    bool: True if `c` is a valid shuffle of `a` and `b`, False otherwise.
    """

    if len(c) != len(a) + len(b):
        return False

    dp = [[False]*(len(b)+1) for _ in range(len(a)+1)]
    dp[0][0] = True

    for i in range(1, len(a)+1):
        dp[i][0] = dp[i-1][0] and a[i-1] == c[i-1]

    for j in range(1, len(b)+1):
        dp[0][j] = dp[0][j-1] and b[j-1] == c[j-1]

    for i in range(1, len(a)+1):
        for j in range(1, len(b)+1):
            with_a = dp[i-1][j] and a[i-1] == c[i+j-1]
            with_b = dp[i][j-1] and b[j-1] == c[i+j-1]
            dp[i][j] = with_a or with_b

    return dp[-1][-1]
