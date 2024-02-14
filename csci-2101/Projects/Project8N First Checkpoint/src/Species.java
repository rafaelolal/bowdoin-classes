import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

/**
 * This class represents a type of creature in a Darwin simulation. All
 * creatures of a given species execute the instructions of that species's
 * Darwin program, which specify how the creatures behave in the world. Each
 * Darwin program consists of a set of ordered instructions, each with an
 * instruction address (starting from address 0, like an index). At any given
 * time, each creature is at some instruction address, and continues executing
 * from that point the next time the creature acts. All creatures of a given
 * species are represented by a particular color.
 */
public class Species {

    List<Instruction> instructions;
    Color color;
    String name;

    /**
     * Create a new species using the given Darwin program and the specified
     * color. May throw a BadSpeciesException if the given file does not exist or
     * does not contain a well-formed Darwin program.
     * 
     * @param fileName The filename of a Darwin program.
     * @param color    The color to use for this species.
     */
    public Species(String fileName, Color color) throws FileNotFoundException {
        this.color = color;
        instructions = new ArrayList<>();
        readInstructions(fileName);
    }

    public Species(String fileName, String color) throws FileNotFoundException {
        this(fileName, colorFromString(color));
    }

    private void readInstructions(String fileName) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(fileName));

        boolean isName = true;
        while (scan.hasNextLine()) {
            String line = scan.nextLine().strip();

            if (line.length() == 0) {
                continue;
            } else if (line.charAt(0) == '#') {
                continue;
            }

            if (isName) {
                name = line;
                isName = false;
            } else if (line.contains(":")) {
                String withoutColon = line.substring(0, line.length() - 1);
                if (isAlphanumeric(withoutColon)) {
                    instructions.add(new Instruction(Opcode.LABEL, withoutColon));
                } else {
                    throw new BadSpeciesException("Label `%s` is not alphanumeric".formatted(line));
                }
            } else {
                String[] instruction = line.split(" ");
                if (instruction.length == 2) {
                    instructions.add(new Instruction(Opcode.valueOf(instruction[0].toUpperCase()), instruction[1]));
                } else if (instruction.length == 1) {
                    instructions.add(new Instruction(Opcode.valueOf(instruction[0].toUpperCase())));
                } else {
                    throw new BadSpeciesException("Instruction `%s` is invalid.".formatted(line));
                }
            }
        }
    }

    private boolean isAlphanumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Get the name of the species.
     * 
     * @return The species name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the color of the species.
     * 
     * @return The species color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Get the number of instructions in the species program.
     * 
     * @return The number of species program instructions.
     */
    public int programSize() {
        return instructions.size();
    }

    /**
     * Get a particular instruction from the species program.
     * 
     * @param address The address of the desired instruction.
     * @return The specified instruction.
     */
    public Instruction programStep(int address) {
        return instructions.get(address);
    }

    /**
     * Get the address of the instruction within the species program corresponding
     * to the given label. Assumes that a label instruction with the given name
     * exists within the species program.
     * 
     * @param label The name of the label to lookup.
     * @return The instruction address of the given label.
     */
    public int getLabelAddress(String label) {
        ListIterator<Instruction> it = instructions.listIterator();
        while (it.hasNext()) {
            Instruction instruction = it.next();
            if (instruction.getOpcode() == Opcode.LABEL && instruction.getLabel().equals(label)) {
                return it.previousIndex();
            }
        }

        throw new BadSpeciesException("Label `%s` not found.".formatted(label));
    }

    /**
     * Construct a string representation of the species program in some reasonable
     * format. Useful for debugging.
     * 
     * @return A string representing the species program.
     */
    @Override
    public String toString() {
        String str = name;

        for (Instruction instruction : instructions) {
            if (instruction.getOpcode() == Opcode.LABEL) {
                str += "\n%s:\n".formatted(instruction.getLabel());
            } else {
                str += instruction.getOpcode();
                if (instruction.getLabel() != null) {
                    str += " " + instruction.getLabel();
                }
                str += "\n";
            }
        }

        return str;
    }

    /**
     * Get the Color specified by the given name. For a list of valid
     * color names, see the javadoc for the Color class.
     *
     * @param colorName The name of the color to lookup.
     * @return The specified color, or null if no such color exists.
     */
    private static Color colorFromString(String colorName) {
        try {
            Field field = Color.class.getField(colorName);
            return (Color) field.get(null);
        } catch (Exception e) {
            return null; // no such color
        }
    }

    /**
     * Tests the functionality of the Species class.
     */
    public static void main(String[] s) {
        throw new UnsupportedOperationException("Please refer to `SpeciesTest.java` file");
    }

    /**
     * An exception indicating that the given Species program file was malformed.
     */
    public static class BadSpeciesException extends RuntimeException {

        /**
         * Construct a new exception indicating that the species program was
         * malformed.
         * 
         * @param msg A message describing the problem.
         */
        public BadSpeciesException(String msg) {
            super(msg); // pass msg to parent constructor
        }

    }

}
