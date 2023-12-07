import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.FileNotFoundException;

import org.junit.Test;

/**
 * Test cases for the Creature class.
 * 
 * @author Rafael Almeida
 */
public class CreatureTest {

    /**
     * Test the toggleHighlight method of the Creature class.
     *
     * @throws FileNotFoundException If the species file is not found.
     */
    @Test
    public void testToggleHighlight() throws FileNotFoundException {
        Creature creature = new Creature(new Species("species/Medusa.txt", "blue"), new World(10, 10),
                new Position(0, 0), Direction.NORTH);
        assertFalse(creature.isHighlighted());
        creature.toggleHighlight();
        assertTrue(creature.isHighlighted());
        assertEquals(Color.MAGENTA, creature.species().getColor());
        creature.toggleHighlight();
        assertFalse(creature.isHighlighted());
    }

    /**
     * Test the species method of the Creature class.
     *
     * @throws FileNotFoundException If the species file is not found.
     */
    @Test
    public void testSpecies() throws FileNotFoundException {
        // Test getting the current species
        Creature creature = new Creature(new Species("species/Medusa.txt", "blue"), new World(10, 10),
                new Position(0, 0), Direction.NORTH);
        Color blue = Utility.colorFromString("blue");
        assertEquals(blue, creature.species().getColor());
    }

    /**
     * Test the direction method of the Creature class.
     *
     * @throws FileNotFoundException If the species file is not found.
     */
    @Test
    public void testDirection() throws FileNotFoundException {
        WorldMap.initialize(15, 15);
        Creature creature = new Creature(new Species("species/Medusa.txt", "blue"), new World(10, 10),
                new Position(0, 0), Direction.NORTH);
        assertEquals(Direction.NORTH, creature.direction());
    }

    /**
     * Test the position method of the Creature class.
     *
     * @throws FileNotFoundException If the species file is not found.
     */
    @Test
    public void testPosition() throws FileNotFoundException {
        Creature creature = new Creature(new Species("species/Medusa.txt", "blue"), new World(10, 10),
                new Position(0, 0), Direction.NORTH);
        assertEquals(new Position(0, 0), creature.position());
    }

    /**
     * Test the setAddress method of the Creature class.
     *
     * @throws FileNotFoundException If the species file is not found.
     */
    @Test
    public void testSetAddress() throws FileNotFoundException {
        Creature creature = new Creature(new Species("species/Medusa.txt", "blue"), new World(10, 10),
                new Position(0, 0), Direction.NORTH);
        creature.setCurrentAddress(5);
        assertEquals(5, creature.getCurrentAddress());
    }
}
