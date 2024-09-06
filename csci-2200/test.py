# Fibsum

# seq: 0 1 1 2 3  5  8  13
# sum: 0 1 2 4 7 12 20
#   i: 0 1 2 3 4 5 6

def fib_sum(i):
    if i == 0:
        return 0

    fib1 = 0
    fib2 = 1
    s = 1
    for _ in range(i-1):
        temp = fib1 + fib2
        s += temp

        fib1 = fib2
        fib2 = temp

    return s

# predefined test cases!!!


n = int(input("Enter a num: "))

for i in range(0, n+1):
    print(f"{i}: {fib_sum(i)}")
