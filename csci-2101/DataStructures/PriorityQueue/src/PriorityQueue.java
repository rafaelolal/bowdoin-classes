import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class PriorityQueue<T extends Comparable<T>> {
    private List<T> queue = new LinkedList<>();

    public PriorityQueue() {

    }

    public void add(T element) {
        ListIterator<T> it = queue.listIterator();
        while (it.hasNext()) {
            T cur = it.next();
            if (cur.compareTo(element) > 0) {
                // have to do this because add inserts after the element I just checked
                // so t would come after cur when it actually has to come before cur.
                if (it.hasPrevious()) {
                    it.previous();
                }
                break;
            }
        }
        it.add(element);
    }

    public T remove() {
        if (queue.isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        return queue.remove(0); // why the first and not the last?
    }
}
