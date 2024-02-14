import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class HashSetTest {

    private HashSet<Integer> hashSet;

    @Before
    public void setUp() {
        hashSet = new HashSet<>(10);
    }

    @Test
    public void testAdd() {
        hashSet.add(1);
        assertTrue(hashSet.contains(1));
    }

    @Test
    public void testAddDuplicates() {
        hashSet.add(1);
        hashSet.add(1);
        assertEquals(1, hashSet.size());
    }

    @Test
    public void testContains() {
        hashSet.add(42);
        assertTrue(hashSet.contains(42));
        assertFalse(hashSet.contains(99));
    }

    @Test
    public void testRemove() {
        hashSet.add(5);
        assertTrue(hashSet.contains(5));

        hashSet.remove(5);
        assertFalse(hashSet.contains(5));
    }

    @Test
    public void testMultipleRemove() {
        for (int i = 0; i < 20; i++) {
            hashSet.add(i);
        }

        hashSet.remove(0);
        hashSet.remove(5);
        hashSet.remove(10);
        hashSet.remove(15);
        hashSet.remove(19);

        for (int i = 0; i < 20; i++) {
            // is this clever or stupid?
            switch (i) {
                case 0:
                case 5:
                case 10:
                case 15:
                case 19:
                    assertFalse(hashSet.contains(i));
                    break;
                default:
                    assertTrue(hashSet.contains(i));
            }
        }

        hashSet.add(500);
        hashSet.add(-1);
        hashSet.add(16);
        hashSet.add(9);
        hashSet.add(20);

        assertTrue(hashSet.contains(500));
        assertTrue(hashSet.contains(-1));
        assertTrue(hashSet.contains(16));
        assertTrue(hashSet.contains(9));
        assertTrue(hashSet.contains(20));
    }

    @Test
    public void testResize() {
        for (int i = 0; i < 20; i++) {
            hashSet.add(i);
        }

        assertTrue(hashSet.contains(14));
        // may fail if you are keeping track of things differently
        assertTrue(hashSet.getLength() > 20);
    }

    @Test
    public void testRemoveResize() {
        for (int i = 0; i < 20; i++) {
            hashSet.add(i);
        }

        for (int i = 0; i < 20; i++) {
            hashSet.remove(i);
        }

        assertTrue(hashSet.getLength() < 20);
    }

    @Test
    public void testRemoveNonExisting() {
        hashSet.add(7);
        assertFalse(hashSet.contains(99));

        try {
            hashSet.remove(99);
        } catch (NoSuchElementException e) {
            assertFalse(hashSet.contains(99));
            return;
        }

        // if this point is reached, a `NoSuchElementException` is missing in your
        // remove method
        assertTrue(false);

    }

    // @Test
    // public void testDummyClass() {
    // HashSet.Dummy dummy = new HashSet.Dummy();
    // assertNotNull(dummy);
    // }
}
