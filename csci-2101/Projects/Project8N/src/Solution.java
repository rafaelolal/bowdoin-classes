class Solution {
    public static void main(String[] args) {
        System.out.println(plusOne(new int[] { 9 }));
    }

    public static int[] plusOne(int[] digits) {
        int i = digits.length - 1;
        int carryOver = 0;
        while (i >= 0) {
            int sum = digits[i] + 1 + carryOver;

            if (sum > 9) {
                digits[i] = 0;
                carryOver = 1;
                i--;
            } else {
                digits[i] = sum;
                break;
            }
        }

        if (i < 0) {
            int[] newDigits = new int[digits.length + 1];
            newDigits[0] = 1;
            for (int j = 0; j < digits.length; j++) {
                newDigits[j + 1] = digits[j];
            }

            return newDigits;
        }

        return digits;
    }

    public static int removeElement(int[] nums, int val) {
        int removed = 0;
        int back = nums.length - 1;
        for (int i = 0; i < nums.length; i++) {
            if (back <= i) {
                break;
            }

            if (nums[i] == val) {
                while (true) {
                    if (nums[back] != val) {
                        break;
                    }
                    removed++;
                    back--;
                }
                nums[i] = nums[back];
                back--;
                removed++;
            }

        }

        return nums.length - removed;
    }

    public static int removeDuplicates(int[] nums) {
        int k = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[k] != nums[i]) {
                nums[++k] = nums[i];
            }
        }

        for (int i : nums) {
            System.out.print(i + ", ");
        }
        System.out.println();
        return k;
    }

    public static int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (true) {
            if (left == right) {
                return nums[left];
            }

            int mid = (left + right) / 2;

            if (nums[mid] >= nums[0]) {
                if (nums[mid] < nums[right]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            } else {
                right = mid;
            }
        }
    }
}