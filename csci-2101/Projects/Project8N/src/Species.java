import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
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
 *
 * @author Rafael Almeida
 */
class Species {

    private List<Instruction> instructions;
    private Color color;
    private String name;

    /**
     * Create a new species using the given Darwin program and the specified
     * color. May throw a BadSpeciesException if the given file does not exist or
     * does not contain a well-formed Darwin program.
     *
     * @param fileName The filename of a Darwin program.
     * @param color    The color to use for this species.
     * @throws FileNotFoundException If the species program file is not found.
     */
    Species(String fileName, Color color) throws FileNotFoundException {
        this.color = color;
        instructions = new ArrayList<>();
        readInstructions(fileName);
    }

    /**
     * Uses the constructor above but accepts a string as a color instead. Used in
     * test cases.
     *
     * @param fileName The filename of a Darwin program.
     * @param color    The color to use for this species.
     * @throws FileNotFoundException If the species program file is not found.
     */
    Species(String fileName, String color) throws FileNotFoundException {
        this(fileName, Utility.colorFromString(color));
    }

    /**
     * Reads instructions from the specified file and populates the instructions
     * list.
     *
     * @param fileName The filename of a Darwin program.
     * @throws FileNotFoundException If the species program file is not found.
     */
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
                Opcode opcode = Opcode.fromString(instruction[0]);
                if (instruction.length == 1) {
                    if (opcode.labelRequired()) {
                        throw new BadSpeciesException("Instruction `%s` does not have a label but requires one.");
                    }

                    instructions.add(new Instruction(Opcode.valueOf(instruction[0].toUpperCase())));
                } else if (instruction.length == 2) {
                    if (!opcode.acceptsLabel()) {
                        throw new BadSpeciesException("Instruction `%s` has a label but does not accept one.");
                    }

                    instructions.add(new Instruction(opcode, instruction[1]));
                } else {
                    throw new BadSpeciesException(
                            "Instruction `%s` cannot have more than 2 arguments.".formatted(line));
                }
            }
        }
    }

    /**
     * Checks if a string is alphanumeric.
     *
     * @param str The string to check.
     * @return True if the string is alphanumeric, false otherwise.
     */
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
    String getName() {
        return name;
    }

    /**
     * Get the color of the species.
     *
     * @return The species color.
     */
    Color getColor() {
        return color;
    }

    /**
     * Get the number of instructions in the species program.
     *
     * @return The number of species program instructions.
     */
    int programSize() {
        return instructions.size();
    }

    /**
     * Get a particular instruction from the species program.
     *
     * @param address The address of the desired instruction.
     * @return The specified instruction.
     */
    Instruction programStep(int address) {
        return instructions.get(address);
    }

    /**
     * Get the address of the instruction within the species program corresponding
     * to the given label. Assumes that a label instruction with the given name
     * exists within the species program.
     *
     * @param label The name of the label to lookup.
     * @return The instruction address of the given label.
     * @throws BadSpeciesException If the label is not found in the species program.
     */
    int getLabelAddress(String label) {
        ListIterator<Instruction> it = instructions.listIterator();
        while (it.hasNext()) {
            Instruction instruction = it.next();
            if (instruction.getOpcode() == Opcode.LABEL && instruction.getLabel().equals(label)) {
                return it.previousIndex();
            }
        }

        throw new BadSpeciesException("Label `%s` not found.".formatted(label)); // TODO: check if a label exists when
                                                                                 // reading the instructions
    }

    /**
     * Construct a string representation of the species program in some reasonable
     * format. Useful for debugging.
     *
     * @return A string representing the species program.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(name);

        for (Instruction instruction : instructions) {
            if (instruction.getOpcode() == Opcode.LABEL) {
                str.append("\n%s:\n".formatted(instruction.getLabel()));
            } else {
                str.append(instruction.getOpcode());
                if (instruction.getLabel() != null) {
                    str.append(" ").append(instruction.getLabel());
                }
                str.append("\n");
            }
        }

        return str.toString();
    }

    /**
     * Tests the functionality of the Species class.
     *
     * @param s Command-line arguments (not used).
     */
    public static void main(String[] s) {
        throw new UnsupportedOperationException("Please refer to `SpeciesTest.java` file");
    }

    /**
     * An exception indicating that the given Species program file was malformed.
     */
    private static class BadSpeciesException extends RuntimeException {

        /**
         * Construct a new exception indicating that the species program was
         * malformed.
         *
         * @param msg A message describing the problem.
         */
        private BadSpeciesException(String msg) {
            super(msg); // pass msg to parent constructor
        }

    }

}
