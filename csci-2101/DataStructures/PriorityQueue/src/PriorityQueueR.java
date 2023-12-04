import java.util.LinkedList;
import java.util.List;

public class PriorityQueueR<T extends Comparable<T>> {
    private List<T> queue = new LinkedList<>();

    public PriorityQueueR() {

    }

    public void add(T element) {
        queue.add(element);
    }

    public T remove() {
        if (queue.isEmpty()) {
            throw new IndexOutOfBoundsException();
        }

        T highestPriority = queue.get(0);
        for (T element : queue) {
            highestPriority = element.compareTo(highestPriority) > 0 ? element : highestPriority;
        }

        queue.remove(highestPriority);
        return highestPriority;
    }
}
