import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * For this project, I left visibility as package instead of public. I learned
 * about it recently and think it is more appropriate. Though, I may be wrong
 * The main class for the Darwin simulation.
 * 
 * This program prompts for species filenames and colors, generates the starting
 * world and
 * creatures, then runs the Darwin simulation.
 * 
 * CSCI 2101 C
 * 12/03/2023
 * Project 8: Darwin
 * 
 * @author Rafael Almeida
 */
public class Darwin {

    /**
     * I think it is appropriate to leave this because you might find it valuable
     */
    private static final boolean DEBUG = false;
    private static final boolean SIMULATE_FAST = false;
    private static final int CREATURE_COUNT = 10 * 1;
    private static final int WORLD_WIDTH = 15;
    private static final int WORLD_HEIGHT = 15;
    private static final int SIMULATION_DELAY = 500;

    /**
     * The main method of the program.
     *
     * @throws FileNotFoundException If a species file is not found.
     * @throws InterruptedException  If the simulation is interrupted.
     */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        World world = new World(WORLD_WIDTH, WORLD_HEIGHT);
        WorldMap.initialize(WORLD_WIDTH, WORLD_HEIGHT);

        List<Species> species = askForSpecies();
        List<Creature> creatures = createCreatures(world, species, CREATURE_COUNT);
        int rounds = 0;
        // Not a functionality that was asked for but I hope you see it is useful
        while (!simulationFinished(creatures)) {
            for (Creature c : creatures) {
                if (DEBUG) {
                    c.toggleHighlight();

                    if (!SIMULATE_FAST) {
                        WorldMap.pause(SIMULATION_DELAY);
                    }

                    c.execute();
                    c.toggleHighlight();
                } else {
                    c.execute();
                }
            }

            if (!SIMULATE_FAST) {
                WorldMap.pause(SIMULATION_DELAY);
            }

            rounds++;
        }

        System.out.println("`%s` species won after %d rounds!".formatted(creatures.get(0).species().getName(), rounds));
    }

    /**
     * Checks if the simulation has finished, i.e., if all creatures in the list
     * belong to the same species.
     *
     * @param creatures The list of creatures to check.
     * @return True if the simulation has finished, false otherwise.
     */
    private static boolean simulationFinished(List<Creature> creatures) {
        Species species = creatures.get(0).species();
        for (Creature c : creatures) {
            if (!species.equals(c.species())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Prompts the user for species filenames and colors.
     *
     * @return A list of Species objects representing the chosen species.
     * 
     * @throws FileNotFoundException If a species file is not found.
     */
    private static List<Species> askForSpecies() throws FileNotFoundException {
        List<Species> species = new ArrayList<>();

        if (DEBUG) {
            species.add(new Species("species/Rafael.txt",
                    Utility.colorFromString("red")));
            species.add(new Species("species/Beyblade.txt", Utility.colorFromString("green")));
            species.add(new Species("species/Medusa.txt", Utility.colorFromString("black")));
            species.add(new Species("species/Flytrap.txt",
                    Utility.colorFromString("blue")));
            species.add(new Species("species/Food.txt",
                    Utility.colorFromString("yellow")));
            species.add(new Species("species/Hop.txt", Utility.colorFromString("pink")));
            species.add(new Species("species/Rover.txt",
                    Utility.colorFromString("orange")));
            return species;
        }

        Scanner scan = new Scanner(System.in);
        // Repeatedly prompting for species files
        // I know you have told me before to not use while-true-loops like this. I
        // really cannot figure out how to use a do-while loop so that inside the loop
        // there are no break statements (like the while-true) or extra conditionals
        // that are comparable or avoided by a true-while
        while (true) {
            System.out.print("Enter a species filename: ");
            String fileName = scan.nextLine();

            if (fileName.equals("")) {
                break;
            }

            Color color;
            // Repeatedly prompting for a valid color
            // Not the same behavior as the demo provided because I do not think it is good.
            // If the invalid color was mistakenly entered, the person may really not want
            // black. Moreover, if a person makes a mistake more than once, now they got
            // different species with same color
            while (true) {
                System.out.print("Enter a species color: ");
                String colorName = scan.nextLine();
                color = Utility.colorFromString(colorName);
                if (color != null) {
                    break;
                }

                System.out.println("Invalid color, try again");
            }

            species.add(new Species(fileName, color));
        }

        scan.close();
        return species;
    }

    /**
     * Creates creatures based on the provided species.
     *
     * @param world         The world in which creatures will be placed.
     * @param species       The list of species from which creatures will be
     *                      created.
     * @param creatureCount The number of creatures to create for each species.
     * 
     * @return A list of Creature objects representing the created creatures.
     */
    private static List<Creature> createCreatures(World world, List<Species> species, int creatureCount) {
        List<Creature> creatures = new ArrayList<>();
        for (Species s : species) {
            for (int i = 0; i < creatureCount; i++) {
                // Repeatedly trying to find an empty random position
                while (true) {
                    Position randomPosition = world.randomPosition();
                    if (world.get(randomPosition) == null) {
                        Creature creature = new Creature(s, world, randomPosition, Direction.random());
                        creatures.add(creature);
                        world.set(randomPosition, creature);
                        WorldMap.drawCreature(creature);
                        break;
                    }
                }
            }
        }

        return creatures;
    }
}
