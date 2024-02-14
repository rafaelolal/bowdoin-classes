/**
 * This class represents one creature in a Darwin simulation. Each creature is
 * of a particular species and has a position and direction within the
 * simulation world. In addition, each creature must remember its current
 * position within its species program, which tells it which instruction to
 * execute next. Lastly, creatures are responsible for drawing themselves in the
 * graphical world map and updating the map appropriately as actions are taken.
 */
public class Creature {

    /**
     * Create a creature of the given species, within the given world, with the
     * indicated starting position and direction.
     */
    public Creature(Species species, World world, Position pos, Direction dir) {
        // TODO
        throw new UnsupportedOperationException("not implemented");
    }

    public Creature() {
    }

    /**
     * Get the current species of the creature.
     * 
     * @return The current creature species.
     */
    public Species species() {
        // TODO
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Get the current direction of the creature.
     * 
     * @return The current creature direction.
     */
    public Direction direction() {
        // TODO
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Get the current position of the creature.
     * 
     * @return The current creature position.
     */
    public Position position() {
        // TODO
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Repeatedly execute instructions from the creature's program until one of
     * the 'terminating' instructions (hop, left, right, or infect) is executed.
     */
    public void execute() {
        // TODO
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Tests the functionality of the Creature class.
     */
    public static void main(String[] args) {
        // TODO
        throw new UnsupportedOperationException("not implemented");
    }

}
