import static org.junit.Assert.assertEquals;

import java.util.ListIterator;

import org.junit.Test;

public class DoublyLinkedListListIteratorTests {

    @Test
    public void hasNext() {
        DoublyLinkedList<Integer> test = new DoublyLinkedList<>();
        ListIterator<Integer> it = test.listIterator();

        assertEquals(false, it.hasNext());
        test.add(Integer.valueOf(1));
        assertEquals(true, it.hasNext());
        it.next();
        assertEquals(false, it.hasNext());
        test.add(Integer.valueOf(2));
        assertEquals(true, it.hasNext());
    }

    @Test
    public void next() {
        DoublyLinkedList<Integer> test = new DoublyLinkedList<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        ListIterator<Integer> it = test.listIterator();
        assertEquals(Integer.valueOf(0), it.next());
        assertEquals(Integer.valueOf(1), it.next());
    }

    @Test
    public void nextIndex() {
        DoublyLinkedList<Integer> test = new DoublyLinkedList<>();
        test.add(Integer.valueOf(10));
        test.add(Integer.valueOf(11));
        test.add(Integer.valueOf(12));
        ListIterator<Integer> it = test.listIterator();
        assertEquals(0, it.nextIndex());
        it.next();
        assertEquals(1, it.nextIndex());
        it.next();
        assertEquals(2, it.nextIndex());
    }

    @Test
    public void hasPrevious() {
        DoublyLinkedList<Integer> test = new DoublyLinkedList<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        test.add(Integer.valueOf(2));
        ListIterator<Integer> it = test.listIterator();
        assertEquals(false, it.hasPrevious());
        it.next();
        assertEquals(true, it.hasPrevious());
        it.next();
        assertEquals(true, it.hasPrevious());
        it.next();
        assertEquals(true, it.hasPrevious());
    }

    @Test
    public void previous() {
        DoublyLinkedList<Integer> test = new DoublyLinkedList<>();
        test.add(Integer.valueOf(0));
        test.add(Integer.valueOf(1));
        ListIterator<Integer> it = test.listIterator();
        it.next();
        it.next();
        assertEquals(Integer.valueOf(1), it.previous());
        assertEquals(Integer.valueOf(0), it.previous());
    }

    @Test
    public void previousIndex() {
        DoublyLinkedList<Integer> test = new DoublyLinkedList<>();
        test.add(Integer.valueOf(10));
        test.add(Integer.valueOf(11));
        test.add(Integer.valueOf(12));
        ListIterator<Integer> it = test.listIterator();

        assertEquals(-1, it.previousIndex());
        it.next();
        assertEquals(0, it.previousIndex());
        it.next();
        assertEquals(1, it.previousIndex());
    }
}
