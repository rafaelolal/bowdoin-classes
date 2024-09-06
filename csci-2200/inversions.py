"""
Rafael
"""


def count_inversions(a):
    """
    return the number of inversions in a, where a is an array of (distinct) numbers
    """

    c = [0]
    ri(a, c)
    return c[0]


def ri(a, c):
    """
    sort and count inversions
    """

    if len(a) <= 1:
        return a

    mid = len(a) // 2

    return merge(ri(a[:mid], c),
                 ri(a[mid:], c),
                 c)


def merge(l, r, c):
    """
    merge
    """

    m = []
    ll = len(l)
    lc = 0
    rc = 0
    while lc < len(l) and rc < len(r):
        if l[lc] > r[rc]:
            m.append(r[rc])
            rc += 1
            c[0] += ll - lc
        else:
            m.append(l[lc])
            lc += 1

    return m + l[lc:] + r[rc:]
