import java.util.ArrayList;
import java.util.List;

public class MaxHeapAgain<T extends Comparable<T>> {
    private List<T> data;

    public MaxHeapAgain() {
        data = new ArrayList<>();
    }

    public void insert(T element) {
        data.add(element);
        heapifyUp(data.size() - 1);
    }

    public T remove() {
        T removed;

        if (data.size() == 1) {
            removed = data.remove(0);
        } else {
            removed = data.set(0, data.remove(data.size() - 1));
        }

        heapifyDown(0);
        return removed;
    }

    private void heapifyUp(int node) {
        if (node == 0) {
            return;
        }

        int parent = (node - 1) / 2;
        if (data.get(node).compareTo(data.get(parent)) < 0) {
            return;
        }

        data.set(parent, data.set(node, data.get(parent)));
        heapifyUp(parent);

    }

    private void heapifyDown(int node) {
        if (node >= data.size() / 2) {
            return;
        }

        int lChild = node * 2 + 1;
        int rChild = node * 2 + 2;
        boolean hasRChild = rChild < data.size();
        int greatestChild = hasRChild && data.get(rChild).compareTo(data.get(lChild)) > 0 ? rChild : lChild;

        if (data.get(node).compareTo(data.get(greatestChild)) > 0) {
            return;
        }

        data.set(greatestChild, data.set(node, data.get(greatestChild)));
        heapifyDown(greatestChild);
    }

    public String toString() {
        return data.toString();
    }
}
