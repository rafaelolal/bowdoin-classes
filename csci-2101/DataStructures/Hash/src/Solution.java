class Solution {

    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
    }

    public static boolean isPalindrome(String s) {
        s = s.toLowerCase();
        int left = 0;
        int right = s.length() - 1;
        while (left <= right) {
            char leftChar = s.charAt(left);
            char rightChar = s.charAt(right);

            if (!Character.isLetterOrDigit(leftChar) && !Character.isLetterOrDigit(rightChar)) {
                left++;
                right--;
            } else if (Character.isLetterOrDigit(leftChar) && !Character.isLetterOrDigit(rightChar)) {
                right--;
            } else if (!Character.isLetterOrDigit(leftChar) && Character.isLetterOrDigit(rightChar)) {
                left++;
            } else if (leftChar != rightChar) {
                return false;
            } else {
                left++;
                right--;
            }
        }

        return true;
    }
}