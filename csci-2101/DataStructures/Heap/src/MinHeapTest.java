import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MinHeapTest {
    @Test
    public void insertTest() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(33);
        heap.insert(35);
        heap.insert(44);
        heap.insert(27);
        heap.insert(42);
        heap.insert(19);
        heap.insert(10);
        heap.insert(31);
        heap.insert(14);
        heap.insert(26);
        assertEquals("[10, 14, 19, 31, 26, 44, 27, 35, 33, 42]", heap.toString());
    }

    @Test
    public void removeTest() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(33);
        heap.insert(35);
        heap.insert(44);
        heap.insert(27);
        heap.insert(42);
        heap.insert(19);
        heap.insert(10);
        heap.insert(31);
        heap.insert(14);
        heap.insert(26);

        assertEquals(Integer.valueOf(10), heap.remove());
        assertEquals(Integer.valueOf(14), heap.remove());
        assertEquals(Integer.valueOf(19), heap.remove());
        assertEquals(Integer.valueOf(26), heap.remove());
        assertEquals(Integer.valueOf(27), heap.remove());
        assertEquals(Integer.valueOf(31), heap.remove());
        assertEquals(Integer.valueOf(33), heap.remove());
        assertEquals(Integer.valueOf(35), heap.remove());
        assertEquals(Integer.valueOf(42), heap.remove());
        assertEquals(Integer.valueOf(44), heap.remove());
    }
}
