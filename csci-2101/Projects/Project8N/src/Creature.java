import java.awt.Color;
import java.io.FileNotFoundException;

/**
 * This class represents one creature in a Darwin simulation. Each creature is
 * of a particular species and has a position and direction within the
 * simulation world. In addition, each creature must remember its current
 * position within its species program, which tells it which instruction to
 * execute next. Lastly, creatures are responsible for drawing themselves in the
 * graphical world map and updating the map appropriately as actions are taken.
 * 
 * @author Rafael Almeida
 */
class Creature {

    private Species species;
    private Species tempSpecies;
    private boolean isHighlighted;
    private World world;
    private Position position;
    private Position previousPosition;
    private Direction direction;
    private int currentAddress;

    /**
     * Create a creature of the given species, within the given world, with the
     * indicated starting position and direction.
     */
    Creature(Species species, World world, Position position, Direction direction) {
        this.species = species;
        this.tempSpecies = species;
        this.isHighlighted = false;
        this.world = world;
        this.position = position;
        this.previousPosition = null;
        this.direction = direction;
        currentAddress = 0;
    }

    Creature() {

    }

    /**
     * Toggles the highlight state of the creature.
     *
     * @throws FileNotFoundException If the species file is not found.
     */
    void toggleHighlight() throws FileNotFoundException {
        if (!isHighlighted) {
            // Create a temporary species with a highlight color (MAGENTA)
            tempSpecies = new Species("species/" + species.getName() + ".txt", Color.MAGENTA);
        } else {
            tempSpecies = species;
        }

        isHighlighted = !isHighlighted;
        WorldMap.drawCreature(this);
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    /**
     * Repeatedly executes instructions from the creature's program until one of
     * the 'terminating' instructions (hop, left, right, or infect) is executed.
     */
    void execute() {
        Position nextPosition = position.getAdjacent(direction);

        Creature nextCreature = null;
        if (world.inBounds(nextPosition)) {
            nextCreature = world.get(nextPosition);
        }

        // Continue executing instructions until an action instruction is
        // encountered
        while (true) {
            Instruction currentInstruction = species.programStep(currentAddress);

            switch (currentInstruction.getOpcode()) {
                case LEFT:
                    direction = direction.left();
                    currentAddress++;
                    WorldMap.drawCreature(this);
                    return;

                case RIGHT:
                    direction = direction.right();
                    currentAddress++;
                    WorldMap.drawCreature(this);
                    return;

                case HOP:
                    // Hop forward if the square in front is in bounds and is unoccupied
                    if (world.inBounds(nextPosition) && world.get(nextPosition) == null) {
                        // Move the creature to the new position
                        previousPosition = position;
                        world.set(previousPosition, null);
                        world.set(nextPosition, this);
                        position = nextPosition;

                        // Redraw the moved creature on the world map
                        WorldMap.drawMovedCreature(this, previousPosition);
                    }
                    currentAddress++;
                    return;

                case INFECT:
                    // Infect the creature in front if it exists and is not the same species
                    if (nextCreature != null && !species.getName().equals(nextCreature.species().getName())) {
                        nextCreature.setSpecies(species);
                        String infectLabel = currentInstruction.getLabel();
                        if (infectLabel != null) {
                            nextCreature.setAddress(nextCreature.species().getLabelAddress(infectLabel));
                        } else {
                            nextCreature.setAddress(0);
                        }

                        WorldMap.drawCreature(nextCreature);
                    }

                    currentAddress++;
                    return;

                case LABEL:
                    currentAddress++;
                    break;

                case GO:
                    currentAddress = species.getLabelAddress(currentInstruction.getLabel());
                    break;

                case IFRANDOM:
                    int randomInteger = (int) (Math.random() * 2 + 1);
                    if (randomInteger == 2) {
                        currentAddress = species.getLabelAddress(currentInstruction.getLabel());
                    } else {
                        currentAddress++;
                    }
                    break;

                case IFEMPTY:
                    if (nextCreature == null) {
                        currentAddress = species.getLabelAddress(currentInstruction.getLabel());
                    } else {
                        currentAddress++;
                    }
                    break;

                case IFWALL:
                    // Execute the next instruction if the creature is a position that is not in
                    // bounds
                    if (!world.inBounds(nextPosition)) {
                        currentAddress = species.getLabelAddress(currentInstruction.getLabel());
                    } else {
                        currentAddress++;
                    }
                    break;

                case IFSAME:
                    // Execute the next instruction if the creature is facing a creature of the same
                    // species
                    if (nextCreature != null && species.getName().equals(nextCreature.species().getName())) {
                        currentAddress = species.getLabelAddress(currentInstruction.getLabel());
                    } else {
                        currentAddress++;
                    }
                    break;

                case IFENEMY:
                    // Execute the next instruction if the creature is facing a creature of a
                    // different species
                    if (nextCreature != null && !species.getName().equals(nextCreature.species().getName())) {
                        currentAddress = species.getLabelAddress(currentInstruction.getLabel());
                    } else {
                        currentAddress++;
                    }

                    break;
            }
        }
    }

    /**
     * Get the current species of the creature.
     * 
     * @return The current creature species.
     */
    Species species() {
        return tempSpecies;
    }

    /**
     * Set the current species of the creature.
     * 
     * @param newSpecies The new species to set to.
     * 
     * @return The old creature species.
     */
    private Species setSpecies(Species newSpecies) {
        Species oldSpecies = species;
        species = newSpecies;
        tempSpecies = newSpecies;
        return oldSpecies;
    }

    /**
     * Get the current direction of the creature.
     * 
     * @return The current creature direction.
     */
    Direction direction() {
        return direction;
    }

    /**
     * Get the current position of the creature.
     * 
     * @return The current creature position.
     */
    Position position() {
        return position;
    }

    /**
     * Set the current instruction of the creature.
     * 
     * @param newAddress The new instruction the creature is at
     */
    void setAddress(int newAddress) {
        currentAddress = newAddress;
    }

    int getCurrentAddress() {
        return currentAddress;
    }

    /**
     * Tests the functionality of the Creature class.
     */
    public static void main(String[] args) {
        throw new UnsupportedOperationException("Please refer to `CreatueTest.java` file");
    }

}
