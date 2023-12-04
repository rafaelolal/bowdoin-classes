import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        List<Integer> list = new ArrayList<>(Arrays.asList(3, 8, 5, 16, 10, 20, 9, 6));
        System.out.println(list.toString());
        list = mergeSort(list);
        System.out.println(list.toString());
    }

    public static List<Integer> mergeSort(List<Integer> list) {
        if (list.size() <= 1) {
            return list;
        }

        List<Integer> left = new ArrayList<>(list.subList(0, list.size() / 2));
        List<Integer> right = new ArrayList<>(list.subList(list.size() / 2, list.size()));

        left = mergeSort(left);
        right = mergeSort(right);

        return sortedMerge(left, right);
    }

    public static List<Integer> sortedMerge(List<Integer> left, List<Integer> right) {
        List<Integer> result = new ArrayList<>();

        while (!(left.isEmpty() || right.isEmpty())) {
            if (left.get(0) <= right.get(0)) {
                result.add(left.remove(0));

            } else {
                result.add(right.remove(0));
            }
        }

        while (!left.isEmpty()) {
            result.add(left.remove(0));
        }

        while (!right.isEmpty()) {
            Integer removed = right.remove(0);
            result.add(removed);
        }

        return result;
    }
}
