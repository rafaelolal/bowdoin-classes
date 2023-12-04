import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for the World class, which represents the environment of the
 * Darwin simulation.
 * 
 * @author Rafael Almeida
 */
public class WorldTest {

    /**
     * Tests the getters of the World class.
     */
    @Test
    public void testGetters() {
        World test = new World(13, 17);
        assertEquals(13, test.width());
        assertEquals(17, test.height());
    }

    /**
     * Tests the inBounds method of the World class.
     */
    @Test
    public void testInBounds() {
        World test = new World(15, 15);
        Position inBound1 = new Position(0, 0);
        Position inBound2 = new Position(14, 14);
        Position inBound3 = new Position(7, 5);
        Position outBound1 = new Position(-1, 0);
        Position outBound2 = new Position(0, 15);
        Position outBound3 = new Position(16, -1);
        assertTrue(test.inBounds(inBound1));
        assertTrue(test.inBounds(inBound2));
        assertTrue(test.inBounds(inBound3));
        assertFalse(test.inBounds(outBound1));
        assertFalse(test.inBounds(outBound2));
        assertFalse(test.inBounds(outBound3));
    }

    /**
     * Tests the randomPosition method of the World class.
     */
    @Test
    public void testRandomPosition() {
        World test = new World(15, 15);
        for (int i = 0; i < test.width() * test.height(); i++) {
            Position pos = test.randomPosition();
            assertTrue(test.inBounds(pos));
        }
    }

    /**
     * Tests the set and get methods of the World class. I know each test should
     * test one thing but since to test set you need to use get, might as well kill
     * two birds with one stone.
     */
    @Test
    public void testSetAndGet() {
        World test = new World(15, 15);
        Position p1 = new Position(0, 0);
        Position p2 = new Position(11, 7);
        Position p3 = new Position(14, 14);
        Position p4 = new Position(5, 13);
        Creature c1 = new Creature();
        Creature c2 = new Creature();
        Creature c3 = new Creature();

        test.set(p1, c1);
        test.set(p2, c2);
        test.set(p3, c3);

        assertEquals(c1, test.get(p1));
        assertEquals(c2, test.get(p2));
        assertEquals(c3, test.get(p3));
        assertEquals(null, test.get(p4));

        test.set(p1, null);
        assertEquals(null, test.get(p1));
    }
}
