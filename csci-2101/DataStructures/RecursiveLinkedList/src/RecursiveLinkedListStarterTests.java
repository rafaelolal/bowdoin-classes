import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RecursiveLinkedListStarterTests {
    @Test
    public void reverse() {

        RecursiveLinkedListStarter<Integer> test = new RecursiveLinkedListStarter<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(7));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(7));
        test.add(Integer.valueOf(2));
        test.add(Integer.valueOf(7));
        test.add(Integer.valueOf(7));
        test.add(Integer.valueOf(2));
        test.reverse();
        assertEquals("[2, 7, 7, 2, 7, 1, 7, 0]", test.toString());
    }

    @Test
    public void replace() {

        RecursiveLinkedListStarter<Integer> test = new RecursiveLinkedListStarter<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(7));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(7));
        test.add(Integer.valueOf(2));
        test.add(Integer.valueOf(7));
        test.add(Integer.valueOf(7));
        test.add(Integer.valueOf(2));
        test.replace(7, 8);
        assertEquals("[0, 8, 1, 8, 2, 8, 8, 2]", test.toString());
    }

    @Test
    public void set() {
        RecursiveLinkedListStarter<Integer> test = new RecursiveLinkedListStarter<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        assertEquals("[0, 1, 2]", test.toString());

        Integer removed = test.set(0, Integer.valueOf(7));
        assertEquals(Integer.valueOf(0), removed);
        assertEquals("[7, 1, 2]", test.toString());

        removed = test.set(2, Integer.valueOf(8));
        assertEquals("[7, 1, 8]", test.toString());
    }

    @Test
    public void get() {
        RecursiveLinkedListStarter<Integer> test = new RecursiveLinkedListStarter<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        test.add(Integer.valueOf(3));
        test.add(Integer.valueOf(4));
        test.add(Integer.valueOf(5));
        test.add(Integer.valueOf(6));
        assertEquals("[0, 1, 2, 3, 4, 5, 6]", test.toString());

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
        RecursiveLinkedListStarter<Integer> test = new RecursiveLinkedListStarter<>();
        assertEquals(true, test.isEmpty());
        test.add(Integer.valueOf(0));
        assertEquals("[0]", test.toString());
        assertEquals(false, test.isEmpty());
        test.remove(0);
        assertEquals(true, test.isEmpty());
        assertEquals("[]", test.toString());
    }

    @Test
    public void remove() {
        RecursiveLinkedListStarter<Integer> test = new RecursiveLinkedListStarter<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        test.add(Integer.valueOf(3));
        assertEquals("[0, 1, 2, 3]", test.toString());

        // removing the head
        Integer removed = test.remove(0);
        assertEquals(Integer.valueOf(0), removed);
        assertEquals(3, test.size());
        assertEquals("[1, 2, 3]", test.toString());

        // removing in the middle
        removed = test.remove(1);
        assertEquals(Integer.valueOf(2), removed);
        assertEquals(2, test.size());
        assertEquals("[1, 3]", test.toString());
    }

    @Test
    public void add() {
        RecursiveLinkedListStarter<Integer> test = new RecursiveLinkedListStarter<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        assertEquals("[0, 1, 2]", test.toString());
        assertEquals(3, test.size());
    }

    @Test
    public void addToIndex() {
        RecursiveLinkedListStarter<Integer> test = new RecursiveLinkedListStarter<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        assertEquals("[0, 1, 2]", test.toString());

        // adding to head
        test.add(0, Integer.valueOf(7));
        assertEquals(4, test.size());
        assertEquals("[7, 0, 1, 2]", test.toString());

        // adding to middle
        test.add(2, Integer.valueOf(8));
        assertEquals(5, test.size());
        assertEquals("[7, 0, 8, 1, 2]", test.toString());
    }

    @Test
    public void size() {
        RecursiveLinkedListStarter<Integer> test = new RecursiveLinkedListStarter<>();

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
