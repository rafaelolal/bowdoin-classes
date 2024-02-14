import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MaxHeapTest {
    @Test
    public void insertTest() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.insert(35);
        heap.insert(33);
        heap.insert(42);
        heap.insert(10);
        heap.insert(14);
        heap.insert(19);
        heap.insert(27);
        heap.insert(44);
        heap.insert(26);
        heap.insert(31);
        assertEquals("[44, 42, 35, 33, 31, 19, 27, 10, 26, 14]", heap.toString());
    }

    @Test
    public void recursiveInsertTest() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.recursiveInsert(35);
        heap.recursiveInsert(33);
        heap.recursiveInsert(42);
        heap.recursiveInsert(10);
        heap.recursiveInsert(14);
        heap.recursiveInsert(19);
        heap.recursiveInsert(27);
        heap.recursiveInsert(44);
        heap.recursiveInsert(26);
        heap.recursiveInsert(31);
        assertEquals("[44, 42, 35, 33, 31, 19, 27, 10, 26, 14]", heap.toString());
    }

    @Test
    public void removeTest() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.insert(35);
        heap.insert(33);
        heap.insert(42);
        heap.insert(10);
        heap.insert(14);
        heap.insert(19);
        heap.insert(27);
        heap.insert(44);
        heap.insert(26);
        heap.insert(31);

        assertEquals(Integer.valueOf(44), heap.remove());
        assertEquals(Integer.valueOf(42), heap.remove());
        assertEquals(Integer.valueOf(35), heap.remove());
        assertEquals(Integer.valueOf(33), heap.remove());
        assertEquals(Integer.valueOf(31), heap.remove());
        assertEquals(Integer.valueOf(27), heap.remove());
        assertEquals(Integer.valueOf(26), heap.remove());
        assertEquals(Integer.valueOf(19), heap.remove());
        assertEquals(Integer.valueOf(14), heap.remove());
        assertEquals(Integer.valueOf(10), heap.remove());
    }

    @Test
    public void recursiveRemoveTest() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.insert(35);
        heap.insert(33);
        heap.insert(42);
        heap.insert(10);
        heap.insert(14);
        heap.insert(19);
        heap.insert(27);
        heap.insert(44);
        heap.insert(26);
        heap.insert(31);

        assertEquals(Integer.valueOf(44), heap.recursiveRemove());
        assertEquals(Integer.valueOf(42), heap.recursiveRemove());
        assertEquals(Integer.valueOf(35), heap.recursiveRemove());
        assertEquals(Integer.valueOf(33), heap.recursiveRemove());
        assertEquals(Integer.valueOf(31), heap.recursiveRemove());
        assertEquals(Integer.valueOf(27), heap.recursiveRemove());
        assertEquals(Integer.valueOf(26), heap.recursiveRemove());
        assertEquals(Integer.valueOf(19), heap.recursiveRemove());
        assertEquals(Integer.valueOf(14), heap.recursiveRemove());
        assertEquals(Integer.valueOf(10), heap.recursiveRemove());
    }
}
