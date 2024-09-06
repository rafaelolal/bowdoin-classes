from assignment8 import is_shuffle


def shuffle_strings(a, b, prefix=""):
    if a == "" and b == "":
        yield prefix
    else:
        if a:
            for result in shuffle_strings(a[1:], b, prefix + a[0]):
                yield result
        if b:
            for result in shuffle_strings(a, b[1:], prefix + b[0]):
                yield result


def is_shuffle0(a, b, c):
    """
    Checks if string 'c' is a valid shuffle of strings 'a' and 'b'.

    A string is a valid shuffle of two other strings if it can be formed by
    interleaving the characters of the two strings while maintaining the
    original order of characters.

    Parameters:
    a (str): The first input string.
    b (str): The second input string.
    c (str): The string to check if it is a valid shuffle of 'a' and 'b'.

    Returns:
    bool: True if 'c' is a valid shuffle of 'a' and 'b', False otherwise.
    """

    if len(c) != len(a) + len(b):
        return False

    ai, bi, ci = 0, 0, 0
    cj = 1

    while ci < len(c):
        prefix = c[ci:cj]

        if cj <= len(c) and (a.startswith(prefix, ai) or b.startswith(prefix, bi)):
            cj += 1
            continue

        if ci == len(c) - 1:
            prev_prefix = prefix

        else:
            prev_prefix = prefix[:-1]

        if not prev_prefix:
            return False

        if a.startswith(prev_prefix, ai):
            ai += len(prev_prefix)

        elif b.startswith(prev_prefix, bi):
            bi += len(prev_prefix)

        else:
            return False

        ci = cj - 1

    return True


def is_shuffle2(a, b, c):
    """
    Checks if string 'c' is a valid shuffle of strings 'a' and 'b'.

    A string is a valid shuffle of two other strings if it can be formed by 
    interleaving the characters of the two strings while maintaining the 
    original order of characters.

    Parameters:
    a (str): The first input string.
    b (str): The second input string.
    c (str): The string to check if it is a valid shuffle of 'a' and 'b'.

    Returns:
    bool: True if 'c' is a valid shuffle of 'a' and 'b', False otherwise.
    """

    if len(c) != len(a) + len(b):
        return False

    ai, bi, ci = 0, 0, 0

    while ci < len(c):
        char = c[ci]

        if a[ai] == char:
            ai += 1

        elif b[bi] == char:
            bi += 1

        else:
            return False

        ci += 1

    return True


def is_shuffle3(a, b, c):
    """
    Checks if string 'c' is a valid shuffle of strings 'a' and 'b'.

    A string is a valid shuffle of two other strings if it can be formed by
    interleaving the characters of the two strings while maintaining the
    original order of characters.

    Parameters:
    a (str): The first input string.
    b (str): The second input string.
    c (str): The string to check if it is a valid shuffle of 'a' and 'b'.

    Returns:
    bool: True if 'c' is a valid shuffle of 'a' and 'b', False otherwise.
    """

    if not c:
        return True

    char = c[0]

    o1 = False
    if a and a[0] == char:
        o1 = is_shuffle(a[1:], b, c[1:])

    if o1:
        return True

    o2 = False
    if b and b[0] == char:
        o2 = is_shuffle(a, b[1:], c[1:])

    if o2:
        return True

    return False


a = "rafael"*2
b = "carlos"*2
fun = is_shuffle
for shuffle in shuffle_strings(a, b):
    result = fun(a, b, shuffle)

    if not result:
        print(shuffle)
        fun(a, b, shuffle)
