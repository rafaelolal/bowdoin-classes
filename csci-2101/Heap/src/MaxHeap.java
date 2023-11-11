import java.util.ArrayList;
import java.util.List;

public class MaxHeap<T extends Comparable<T>> {
    // heapifyUp, heapifyDown methods?

    private List<T> data;

    public MaxHeap() {
        data = new ArrayList<>();
    }

    public void insert(T element) {
        data.add(element);

        // is there a way that I can avoid repeating the definition of parent?
        // maybe also the definition of i?
        // can't use do while because here sometimes it is appropriate to
        // make no operations, the do while always does one operation
        int i = data.size() - 1;
        int parent = (int) Math.floor((i - 1) / 2);
        while (data.get(parent).compareTo(element) < 0) {
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
            // this is pretty big, should it become its own method?
            int lChild = 2 * i + 1;
            int rChild = 2 * (i + 1);
            boolean hasLChild = lChild < data.size();
            boolean hasRChild = rChild < data.size();
            boolean lChildIsGreater = hasLChild && data.get(lChild).compareTo(data.get(i)) > 0;
            boolean rChildIsGreater = hasRChild && data.get(rChild).compareTo(data.get(i)) > 0;
            boolean isUnordered = lChildIsGreater || rChildIsGreater;

            if (!isUnordered) {
                break;
            }

            int replaceWith = rChild;
            // have to do lChild >= rChild cuz of preference for lChild?
            if (!hasRChild || data.get(lChild).compareTo(data.get(rChild)) >= 0) {
                replaceWith = lChild;
            }

            // should this operation become its own swap method?
            data.set(replaceWith, data.set(i, data.get(replaceWith)));
            i = replaceWith;
        }

        return removed;
    }

    public String toString() {
        return data.toString();
    }
}
