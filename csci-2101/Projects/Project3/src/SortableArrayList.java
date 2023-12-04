import java.util.Comparator;

/**
 * Our own implementation of an ArrayList. Is missing a few methods, but has the
 * added sort method which is an implementation of insertion sort.
 */
public class SortableArrayList<T> extends SimpleArrayList<T> {

    /**
     * Construct a new list with the given starting capacity.
     * 
     * @param startingCapacity The desired starting list capacity.
     */
    public SortableArrayList(int startingCapacity) {
        super(startingCapacity);
    }

    /**
     * Construct a new list with the default starting capacity.
     */
    public SortableArrayList() {
        super();
    }

    /**
     * Implementation of insertion sort that has the left of the array as the sorted
     * portion
     */
    public void sort(Comparator<? super T> c) {
        for (int i = 0; i < size() - 1; i++) {
            int min = i;
            for (int j = i + 1; j < size(); j++) {
                if (c.compare(get(j), get(min)) < 0) {
                    min = j;
                }
            }
            T minItem = get(min);
            set(min, get(i));
            set(i, minItem);
        }
    }

}
