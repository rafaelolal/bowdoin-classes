import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Rafael Almeida
 * CSCI 2101 C
 * 09/27/2023
 * Project 2: Infinite Monkey Theorem
 * 
 * Handles taking input, building main program data structure, and generating
 * the final output
 * 
 * @param CHARACTERS_TO_GENERATE represents the length of the output
 */
public class WordGen {
    final static int CHARACTERS_TO_GENERATE = 500;

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        // getting file name, reading file, and handling errors
        String fileContent = null; // defaulting fileContent to avoid variable not initialized error
        System.out.print("Enter file to read: ");
        if (scan.hasNextLine()) {
            String fileName = scan.nextLine();
            fileContent = readFileAsString(fileName);
            if (fileContent == null) { // means an IOException ocurred
                System.out.println("File could not be read.");
                System.exit(0);
            }
        } else {
            System.out.println("Invalid file name.");
            System.exit(0);
        }

        // getting value of k, and handling errors
        int k = 0; // defaulting k to avoid variable not initialized error
        System.out.print("Enter desired value of k: ");
        if (scan.hasNextInt()) {
            k = scan.nextInt();
            if (k < 1 || k >= fileContent.length()) { // k also cannot be greater than or equal to the fileContent
                                                      // length because there will be no following character
                System.out.println("Invalid value for k.");
                System.exit(0);
            }
        } else {
            System.out.println("Invalid integer.");
            System.exit(0);
        }

        scan.close();

        // building SequenceTable
        // altering input
        String finalKCharacters = fileContent.substring(fileContent.length() - k);
        fileContent = finalKCharacters + " " + fileContent;

        SequenceTable sequenceTable = new SequenceTable();
        for (int i = 0; i < fileContent.length() - k - 1; i++) {
            String currentSequence = fileContent.substring(i, i + k);
            String followingCharacter = fileContent.substring(i + k, i + k + 1);
            sequenceTable.put(currentSequence, followingCharacter);
        }

        // building output
        StringBuilder output = new StringBuilder();
        String originalFirstKCharacters = fileContent.substring(k + 1, k + k + 1);
        output.append(originalFirstKCharacters);
        for (int i = 0; i < CHARACTERS_TO_GENERATE - k; i++) { // subtracting k from CHARACTERS_TO_GENERATE to ensure
                                                               // output is exactly CHARACTERS_TO_GENERATE in length
            String endSequence = output.substring(output.length() - k);
            output.append(sequenceTable.getNextCharacter(endSequence));
        }

        System.out.println(output);
    }

    /**
     * Read the contents of a file into a string. If the file does not
     * exist or cannot not be read for any reason, returns null.
     *
     * @param filename The name of the file to read.
     * @return The contents of the file as a string, or null.
     */
    private static String readFileAsString(String filename) {
        try {
            return Files.readString(Paths.get(filename));
        } catch (IOException e) {
            return null;
        }
    }
}