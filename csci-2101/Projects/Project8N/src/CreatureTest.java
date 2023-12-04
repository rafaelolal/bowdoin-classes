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
        // Test toggling highlight
        Creature creature = new Creature(new Species("species/Medusa.txt", "blue"), new World(10, 10),
                new Position(0, 0), Direction.NORTH);
        assertFalse(creature.isHighlighted());
        creature.toggleHighlight();
        assertTrue(creature.isHighlighted());
        creature.toggleHighlight();
        assertFalse(creature.isHighlighted());
    }

    /**
     * Test the execute method of the Creature class.
     *
     * @throws FileNotFoundException If the species file is not found.
     */
    @Test
    public void testExecute() throws FileNotFoundException {
        WorldMap.initialize(10, 10);
        // Test execution of creature actions
        World world = new World(10, 10);
        Creature creature = new Creature(new Species("species/Medusa.txt", "blue"), world, new Position(0, 0),
                Direction.NORTH);
        world.set(new Position(0, 1), new Creature(new Species("species/Rover.txt", "red"), world,
                new Position(0, 1), Direction.SOUTH));
        creature.execute(); // Assuming Medusa.txt contains valid instructions

        // You might want to add more specific assertions based on the actions performed
        // in the execute method
        // For example, check if the creature has moved, turned, or infected another
        // creature as expected
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
        // Test getting the current direction
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
        // Test getting the current position
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
        // Test setting the current instruction address
        Creature creature = new Creature(new Species("species/Medusa.txt", "blue"), new World(10, 10),
                new Position(0, 0), Direction.NORTH);
        creature.setAddress(5);
        assertEquals(5, creature.getCurrentAddress());
    }
}
