import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DoublyLinkedListTests {
    @Test
    public void set() {
        DoublyLinkedList<Integer> test = new DoublyLinkedList<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        assertEquals("[0, 1, 2]", test.toString());
        assertEquals("[2, 1, 0]", test.reverseToString());

        Integer removed = test.set(0, Integer.valueOf(7));
        assertEquals(Integer.valueOf(0), removed);
        assertEquals("[7, 1, 2]", test.toString());
        assertEquals("[2, 1, 7]", test.reverseToString());

        removed = test.set(2, Integer.valueOf(8));
        assertEquals("[7, 1, 8]", test.toString());
        assertEquals("[8, 1, 7]", test.reverseToString());
    }

    @Test
    public void get() {
        DoublyLinkedList<Integer> test = new DoublyLinkedList<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        test.add(Integer.valueOf(3));
        test.add(Integer.valueOf(4));
        test.add(Integer.valueOf(5));
        test.add(Integer.valueOf(6));
        assertEquals("[0, 1, 2, 3, 4, 5, 6]", test.toString());
        assertEquals("[6, 5, 4, 3, 2, 1, 0]", test.reverseToString());

        assertEquals(Integer.valueOf(0), test.get(0));
        assertEquals(Integer.valueOf(1), test.get(1));
        assertEquals(Integer.valueOf(2), test.get(2));
        assertEquals(Integer.valueOf(3), test.get(3));
        assertEquals(Integer.valueOf(4), test.get(4));
        assertEquals(Integer.valueOf(5), test.get(5));
        assertEquals(Integer.valueOf(6), test.get(6));
    }

    @Test
    public void isEmpty() {
        DoublyLinkedList<Integer> test = new DoublyLinkedList<>();
        assertEquals(true, test.isEmpty());
        test.add(Integer.valueOf(0));
        assertEquals("[0]", test.toString());
        assertEquals("[0]", test.reverseToString());
        assertEquals(false, test.isEmpty());
        test.remove(0);
        assertEquals(true, test.isEmpty());
        assertEquals("[]", test.toString());
        assertEquals("[]", test.reverseToString());
    }

    @Test
    public void remove() {
        DoublyLinkedList<Integer> test = new DoublyLinkedList<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        test.add(Integer.valueOf(3));
        assertEquals("[0, 1, 2, 3]", test.toString());
        assertEquals("[3, 2, 1, 0]", test.reverseToString());

        // removing the head
        Integer removed = test.remove(0);
        assertEquals(Integer.valueOf(0), removed);
        assertEquals(3, test.size());
        assertEquals("[1, 2, 3]", test.toString());
        assertEquals("[3, 2, 1]", test.reverseToString());

        // removing in the middle
        removed = test.remove(1);
        assertEquals(Integer.valueOf(2), removed);
        assertEquals(2, test.size());
        assertEquals("[1, 3]", test.toString());
        assertEquals("[3, 1]", test.reverseToString());
    }

    @Test
    public void add() {
        DoublyLinkedList<Integer> test = new DoublyLinkedList<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        assertEquals("[0, 1, 2]", test.toString());
        assertEquals("[2, 1, 0]", test.reverseToString());
        assertEquals(3, test.size());
    }

    @Test
    public void addToIndex() {
        DoublyLinkedList<Integer> test = new DoublyLinkedList<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        assertEquals("[0, 1, 2]", test.toString());
        assertEquals("[2, 1, 0]", test.reverseToString());

        // adding to head
        test.add(0, Integer.valueOf(7));
        assertEquals(4, test.size());
        assertEquals("[7, 0, 1, 2]", test.toString());
        assertEquals("[2, 1, 0, 7]", test.reverseToString());

        // adding to middle
        test.add(2, Integer.valueOf(8));
        assertEquals(5, test.size());
        assertEquals("[7, 0, 8, 1, 2]", test.toString());
        assertEquals("[2, 1, 8, 0, 7]", test.reverseToString());
    }

    @Test
    public void size() {
        DoublyLinkedList<Integer> test = new DoublyLinkedList<>();

        assertEquals(0, test.size());
        test.add(Integer.valueOf(0));
        assertEquals(1, test.size());
        test.add(Integer.valueOf(1));
        assertEquals(2, test.size());
        test.add(Integer.valueOf(2));
        assertEquals(3, test.size());
        test.remove(2);
        assertEquals(2, test.size());
        test.remove(0);
        assertEquals(1, test.size());
    }
}
