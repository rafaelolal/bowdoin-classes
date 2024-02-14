import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents the two-dimensional world during a Darwin simulation.
 * Each position of the world may be populated either by nothing or by a single
 * creature.
 */
public class World {

    private int width;
    private int height;
    private List<List<Creature>> board;
    private Random rand;

    /**
     * Create a new world consisting of width columns and height rows. Initially,
     * the world contains no creatures.
     * 
     * @param width  The width of the world.
     * @param height The height of the world.
     */
    public World(int width, int height) {
        this.width = width;
        this.height = height;
        board = new ArrayList<>();
        rand = new Random();

        for (int r = 0; r < height; r++) {
            board.add(new ArrayList<Creature>());
            for (int c = 0; c < width; c++) {
                board.get(r).add(null);
            }
        }
    }

    /**
     * Get the width of the world.
     * 
     * @return The world width.
     */
    public int width() {
        return width;
    }

    /**
     * Get the height of the world.
     * 
     * @return The world height.
     */
    public int height() {
        return height;
    }

    /**
     * Check whether the given position is within the bounds of the world (i.e.,
     * its x and y coordinates specify a valid world position).
     * 
     * @param pos The position to check.
     * @return Whether the given position is within the world bounds.
     */
    public boolean inBounds(Position pos) {
        return (pos.getX() >= 0 && pos.getX() < width) && (pos.getY() >= 0 && pos.getY() < height);
    }

    /**
     * Get a random position within the bounds of the world. All valid world
     * positions (occupied or not) are returned with equal probability.
     * 
     * @return A random position within the world.
     */
    public Position randomPosition() {
        int x = rand.nextInt(width);
        int y = rand.nextInt(height);

        return new Position(x, y); // TODO: return new or existing instance?
    }

    /**
     * Update the given world position to contain the given creature (which may be
     * null, in which case the world position is cleared).
     * 
     * @param pos      The position to update.
     * @param creature The creature to place at the given position, or null.
     */
    public void set(Position pos, Creature creature) {
        board.get(pos.getY()).set(pos.getX(), creature);
    }

    /**
     * Get the creature at the given position of the board, or null if no creature
     * occupies that position.
     * 
     * @param pos The position to get.
     * @return The creature at the specified position, or null.
     */
    public Creature get(Position pos) {
        return board.get(pos.getY()).get(pos.getX());
    }

    /**
     * Tests the functionality of the World class.
     */
    public static void main(String[] args) {
        throw new UnsupportedOperationException("Please refer to `WorldTest.java` file");
    }

}
