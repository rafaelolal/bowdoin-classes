public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Factorials:");
        for (int i = 0; i < 15; i++) {
            System.out.println(i + "! = " + factorial(i));
        }

        System.out.println("\nFibonacci:");

        for (int i = 0; i < 15; i++) {
            System.out.println("fib(" + i + ") = " + fibonacci(i));
        }

        int[] arr = { 1, 5, 7, 3, 4, 10, 6, 12 };
        for (int i = 0; i < arr.length; i++) {
            if (i != search(arr[i], arr)) {
                System.out.println(i + " Search doesn't work " + search(arr[i], arr));
            }
        }
        System.out.println("Search works!");

        int[] sortedArr = { 5, 6, 10, 15, 20, 21, 22, 23, 26, 28 };
        for (int i = 0; i < sortedArr.length; i++) {
            if (i != binarySearch(sortedArr[i], sortedArr)) {
                System.out.println("Doesn't work");
            }
        }
        System.out.println("Binary search works");

    }

    private static int factorial(int a) {
        if (a == 0) {
            return 1;
        }

        return multiply(a, factorial(a - 1));
    }

    private static int multiply(int a, int b) {
        if (a == 1) {
            return b;
        }

        return b + multiply(a - 1, b);
    }

    private static int fibonacci(int n) {
        if (0 == n || 1 == n) {
            return 1;
        }

        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    private static int search(int n, int[] arr) {
        return recursiveSearch(n, arr, 0, arr.length);
    }

    private static int recursiveSearch(int n, int[] arr, int left, int right) {
        if (n == arr[left]) {
            return left;
        } else if (left == right) {
            return -1;
        }

        return recursiveSearch(n, arr, left + 1, right);
    }

    /**
     * Find index of n in arr or returns -1
     * arr must be sorted
     */
    private static int binarySearch(int n, int[] arr) {
        return recursiveBinarySearch(n, arr, 0, arr.length);
    }

    private static int recursiveBinarySearch(int n, int[] arr, int leftBound, int rightBound) {
        int i = (leftBound + rightBound) / 2;
        if (n == arr[i]) {
            return i;
        } else if (n > arr[i]) {
            return recursiveBinarySearch(n, arr, i, rightBound);
        } else if (n < arr[i]) {
            return recursiveBinarySearch(n, arr, leftBound, i);
        } else {
            return -1;
        }
    }
}
