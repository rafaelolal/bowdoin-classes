package Practice;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.lang.IllegalArgumentException;
import java.sql.Array;

public class CollectionPractice {
    public static void main(String[] args) {
        // testing removeFromArray
        // Integer[] test = { 0, 1, 2, 3, 4, 5, 6 };
        // System.out.println(Arrays.toString(test));
        // Object removed = removeFromArray(test, 3);
        // System.out.println(Arrays.toString(test));
        // System.out.println("Removed: " + removed);
        // removeFromArray(test, -1);
        // removeFromArray(test, 8);

        // testing reverseArray and insertIntoArray
        List<Integer> myList = Arrays.asList(1, null, 3, 4, null, null);
        // reverseArray(myList);
        Integer[] myArr = myList.toArray(new Integer[myList.size()]);
        insertIntoArray(myArr, 7, 1);
        for (Integer i : myArr) {
            System.out.print(i + " ");
        }
        // System.out.println(myArr.toString());

        // testing isSubset and symmDiff
        // Set<Integer> set1 = Set.of(1, 2, 3, 4, 5);
        // Set<Integer> set2 = Set.of(4, 5);
        // Set<Integer> set3 = Set.of(5, 6, 7, 8);
        // System.out.println(isSubset(set1, set2));
        // System.out.println(isSubset(set1, set3));
        // System.out.println(symmDiff(set1, set2).toString());
        // System.out.println(symmDiff(set1, set3).toString());

    }

    public static Object removeFromArray(Object[] arr, int idx) {
        if (idx < 0 || idx >= arr.length) {
            throw new IllegalArgumentException("Index to remove must be greater than zero and less than array length!");
        }

        Object removed = arr[idx];

        for (int i = idx; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1];
        }

        arr[arr.length - 1] = null;

        return removed;
    }

    public static void reverseArray(List<Integer> list) {
        for (int i = 0; i < list.size() / 2; i++) {
            int end = list.size() - i - 1;
            Integer temp = list.get(end);
            list.set(end, list.get(i));
            list.set(i, temp);
        }
    }

    public static void insertIntoArray(Integer[] arr, Integer item, int idx) {
        boolean containsValidNull = false;
        for (int i = idx; i < arr.length; i++) {
            if (arr[i] == null) {
                containsValidNull = true;
                break;
            }
        }

        if (!containsValidNull) {
            throw new IllegalArgumentException("Array does not contain a valid null item.");
        }

        int maxIdx = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                break;
            }
            maxIdx = i + 1;
        }

        if (idx > maxIdx) {
            throw new IllegalArgumentException("`idx` must be <= " + maxIdx + ".");
        }

        for (int i = maxIdx; i > idx; i--) {
            arr[i] = arr[i - 1];
        }
        arr[idx] = item;
    }

    public static boolean isSubset(Set<Integer> set1, Set<Integer> set2) {
        for (Integer item : set2) {
            if (!set1.contains(item)) {
                return false;
            }
        }
        return true;
    }

    public static Set<Integer> symmDiff(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> symmDiff = new HashSet<>();
        Set<Integer> intersection = new HashSet<>();
        symmDiff.addAll(set1);
        symmDiff.addAll(set2);
        intersection.addAll(set1);
        intersection.retainAll(set2);
        symmDiff.removeAll(intersection);
        return symmDiff;
    }
}
