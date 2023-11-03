import java.util.Iterator;
import java.util.Stack;

public class App {
    public static void main(String[] args) throws Exception {
        String s = "these are some words ";
        WordIterator iter = new WordIterator(s);
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

    public static class WordIterator implements Iterator<String> {
        private int spaceIndex;
        private String s;

        public WordIterator(String s) {
            spaceIndex = 0;
            this.s = s;
        }

        public boolean hasNext() {
            return spaceIndex != -1;
        }

        public String next() {
            int left = spaceIndex + (spaceIndex == 0 ? 0 : 1);
            int right = s.indexOf(" ", left);

            spaceIndex = right;

            return s.substring(left, right == -1 ? s.length() : right);
        }
    }

    public static void triangle(int n) {
        if (n == 0) {
            return;
        }

        System.out.println("*".repeat(n));
        triangle(n - 1);
    }

    // notice how any code that happens after the recursive call is backwards
    public static void oppositeTriangles(int n) {
        if (n == 0) {
            return;
        }

        System.out.println("*".repeat(n));
        oppositeTriangles(n - 1);
        System.out.println("*".repeat(n));
    }

    public static boolean balParens(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                stack.push('(');
            } else {
                if (stack.size() == 0) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.size() == 0;
    }

    public static void subsetSum(int[] a) {
        recursiveSubsetSum(a, 0, 1);
    }

    private static void recursiveSubsetSum(int[] a, int left, int right) {
        int sum = 0;

        if (left == a.length) {
            System.out.println(sum);
            return;
        }

        for (int i = left; i < right; i++) {
            sum += a[i];
        }

        System.out.println(sum);

        if (right == a.length) {
            recursiveSubsetSum(a, left + 1, left + 2);
            return;
        }

        recursiveSubsetSum(a, left, right + 1);

    }

    // any code that happens after the recursive call is walking backwards
    // n /2 > 35 because even if the number is greater than 35, I still want to
    // print it, i just wanna make sure that the next one is not printed
    public static void pattern(int n) {
        int n2 = n * 2;
        if (n / 2 > 35) {
            return;
        }
        System.out.println(n);
        pattern(n2);
        System.out.println(n);
    }
}
