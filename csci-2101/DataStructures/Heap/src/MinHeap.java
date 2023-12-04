import java.util.ArrayList;
import java.util.List;

public class MinHeap<T extends Comparable<T>> {
    private List<T> data;

    public MinHeap() {
        data = new ArrayList<>();
    }

    public void insert(T element) {
        data.add(element);

        int i = data.size() - 1;
        int parent = (int) Math.floor((i - 1) / 2);
        while (element.compareTo(data.get(parent)) < 0) {
            data.set(i, data.set(parent, element));
            i = parent;
            parent = (int) Math.floor((i - 1) / 2);
        }
    }

    public T remove() {
        if (data.size() == 1) {
            return data.remove(0);
        }

        T removed = data.set(0, data.remove(data.size() - 1));
        int i = 0;
        while (true) {
            int lChild = 2 * i + 1;
            int rChild = 2 * (i + 1);
            boolean hasLChild = lChild < data.size();
            boolean hasRChild = rChild < data.size();
            boolean lChildIsLess = hasLChild && data.get(lChild).compareTo(data.get(i)) < 0;
            boolean rChildIsLess = hasRChild && data.get(rChild).compareTo(data.get(i)) < 0;
            boolean isUnordered = lChildIsLess || rChildIsLess;

            if (!isUnordered) {
                break;
            }

            int replaceWith = rChild;
            if (!hasRChild || data.get(lChild).compareTo(data.get(rChild)) <= 0) {
                replaceWith = lChild;
            }

            data.set(replaceWith, data.set(i, data.get(replaceWith)));
            i = replaceWith;
        }

        return removed;
    }

    public String toString() {
        return data.toString();
    }
}
