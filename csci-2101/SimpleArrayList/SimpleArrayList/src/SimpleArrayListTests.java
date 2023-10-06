import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SimpleArrayListTests {
    @Test
    public void set() {
        SimpleArrayList<Integer> test = new SimpleArrayList<>(new Integer[] { 1, 2, 3, 4 });
        Integer removed = test.set(1, 7);
        assertEquals(removed, Integer.valueOf(2));
        assertEquals(test.get(1), Integer.valueOf(7));
    }

    @Test
    public void get() {
        SimpleArrayList<Integer> test = new SimpleArrayList<>(new Integer[] { 1, 2, 3, 4 });
        Integer gotten = test.get(1);
        assertEquals(gotten, Integer.valueOf(2));
    }

    @Test
    public void isEmpty() {
        SimpleArrayList<Integer> test = new SimpleArrayList<>(new Integer[] {});
        SimpleArrayList<Integer> test1 = new SimpleArrayList<>(new Integer[] { 1 });
        assertEquals(test.isEmpty(), true);
        assertEquals(test1.isEmpty(), false);
    }

    @Test
    public void remove() {
        SimpleArrayList<Integer> test = new SimpleArrayList<>(new Integer[] { 1, 2, 3, 4 });
        Integer removed = test.remove(1);
        assertEquals(removed, Integer.valueOf(2));
        assertEquals(test.size(), 3);
    }

    @Test
    public void add() {
        SimpleArrayList<Integer> test = new SimpleArrayList<>(new Integer[] { 1, 2, 3, 4 });
        test.add(7);
        assertEquals(test.get(4), Integer.valueOf(7));
        assertEquals(test.size(), 5);
    }

    @Test
    public void addToIndex() {
        SimpleArrayList<Integer> test = new SimpleArrayList<>(new Integer[] { 1, 2, 3, 4 });
        test.add(1, 7);

        assertEquals(test.get(1), Integer.valueOf(7));
        assertEquals(test.get(0), Integer.valueOf(1));
        assertEquals(test.get(2), Integer.valueOf(2));
        assertEquals(test.get(3), Integer.valueOf(3));
        assertEquals(test.get(4), Integer.valueOf(4));
        assertEquals(test.size(), 5);
    }

    @Test
    public void size() {
        SimpleArrayList<Integer> test = new SimpleArrayList<>(new Integer[] { 1, 2, 3, 4 });
        assertEquals(test.size(), 4);
    }
}
