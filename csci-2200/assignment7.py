def haseqs(vals):
    """
    PARAMETER vals: an array of values, all positive integers
    RETURN: true if vals can be partitioned into two subsets such that the
    sum of the elements in both subsets is equal. False otherwise.
    """

    half_sum = sum(vals)/2

    if half_sum != int(half_sum):
        return False

    return subset_sum(vals, 0, half_sum)


def subset_sum(vals, i, target):
    if target == 0:
        return True

    if target < 0:
        return False

    if i == len(vals):
        return False

    _with = subset_sum(vals, i+1, target - vals[i])

    if _with:
        return True

    without = subset_sum(vals, i+1, target)

    if without:
        return True

    return False


def haseqs_dp(vals):
    """
    PARAMETER vals: an array of values, all positive integers
    RETURN: true if vals can be partitioned into two subsets such that the
    sum of the elements in both subsets is equal. False otherwise.
    """

    total_sum = sum(vals)
    half_sum = total_sum / 2

    if half_sum != int(half_sum):
        return False

    half_sum = int(half_sum)

    table = [[None]*(half_sum + 1) for _ in range(len(vals))]
    for i in range(len(vals)):
        table[i][0] = True

    return subset_sum_dp(vals, 0, half_sum, table)


def subset_sum_dp(vals, i, target, table):
    if target == 0:
        return True

    if target < 0 or i == len(vals):
        return False

    if table[i][target] is not None:
        return table[i][target]

    _with = subset_sum_dp(vals, i + 1, target - vals[i], table)

    if _with:
        table[i][target] = True
        return table[i][target]

    without = subset_sum_dp(vals, i + 1, target, table)

    if without:
        table[i][target] = True
        return table[i][target]

    table[i][target] = False
    return table[i][target]


def haseqs_full(vals):
    """
    PARAMETER vals: an array of values, all positive integers
    RETURN: returns a subset of vals whose sum is half the sum of val if possible
    else an empty list
    """

    total_sum = sum(vals)
    half_sum = total_sum / 2

    if half_sum != int(half_sum):
        return []

    half_sum = int(half_sum)

    table = [[None]*(half_sum + 1) for _ in range(len(vals))]
    for i in range(len(vals)):
        table[i][0] = True

    cur = []
    subset_sum_full(vals, 0, half_sum, table, cur)
    return cur


def subset_sum_full(vals, i, target, table, cur):
    if target == 0:
        return True

    if target < 0 or i == len(vals):
        return False

    if table[i][target] is not None:
        return table[i][target]

    cur.append(vals[i])
    _with = subset_sum_full(vals, i + 1, target - vals[i], table, cur)

    if _with:
        table[i][target] = True
        return table[i][target]

    cur.pop()
    without = subset_sum_full(vals, i + 1, target, table, cur)

    if without:
        table[i][target] = True
        return table[i][target]

    table[i][target] = False
    return table[i][target]
