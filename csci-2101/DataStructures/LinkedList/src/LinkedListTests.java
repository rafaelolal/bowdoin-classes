import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LinkedListTests {
    @Test
    public void set() {
        LinkedList<Integer> test = new LinkedList<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        Integer removed = test.set(0, Integer.valueOf(7));
        assertEquals(removed, Integer.valueOf(0));
        assertEquals(test.get(0), Integer.valueOf(7));
        removed = test.set(2, Integer.valueOf(8));
        assertEquals(test.get(1), Integer.valueOf(1));
        assertEquals(test.get(2), Integer.valueOf(8));
    }

    @Test
    public void get() {
        LinkedList<Integer> test = new LinkedList<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        assertEquals(test.get(0), Integer.valueOf(0));
        assertEquals(test.get(1), Integer.valueOf(1));
        assertEquals(test.get(2), Integer.valueOf(2));
    }

    @Test
    public void isEmpty() {
        LinkedList<Integer> test = new LinkedList<>();
        assertEquals(test.isEmpty(), true);
        test.add(Integer.valueOf(0));
        assertEquals(test.isEmpty(), false);
        test.remove(0);
        assertEquals(test.isEmpty(), true);
    }

    @Test
    public void remove() {
        LinkedList<Integer> test = new LinkedList<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        test.add(Integer.valueOf(3));

        // removing the head
        Integer removed = test.remove(0);
        assertEquals(removed, Integer.valueOf(0));
        assertEquals(test.size(), 3);
        assertEquals(test.get(0), Integer.valueOf(1));
        assertEquals(test.get(1), Integer.valueOf(2));
        assertEquals(test.get(2), Integer.valueOf(3));

        // removing in the middle
        removed = test.remove(1);
        assertEquals(removed, Integer.valueOf(2));
        assertEquals(test.size(), 2);
        assertEquals(test.get(0), Integer.valueOf(1));
        assertEquals(test.get(1), Integer.valueOf(3));
    }

    @Test
    public void add() {
        LinkedList<Integer> test = new LinkedList<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        assertEquals(test.size(), 3);
    }

    @Test
    public void addToIndex() {
        LinkedList<Integer> test = new LinkedList<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        test.add(0, Integer.valueOf(7));
        assertEquals(test.get(0), Integer.valueOf(7));
        test.add(2, Integer.valueOf(8));
        assertEquals(test.get(2), Integer.valueOf(8));
    }

    @Test
    public void size() {
        LinkedList<Integer> test = new LinkedList<>();

        assertEquals(test.size(), 0);
        test.add(Integer.valueOf(0));
        assertEquals(test.size(), 1);
        test.add(Integer.valueOf(1));
        assertEquals(test.size(), 2);
        test.add(Integer.valueOf(2));
        assertEquals(test.size(), 3);
        test.remove(2);
        assertEquals(test.size(), 2);
        test.remove(0);
        assertEquals(test.size(), 1);
    }
}
