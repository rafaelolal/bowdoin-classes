import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * A class representing a 4x4 board in the game of Boggle.
 * Code is complete except for the findWords method, which must
 * be implemented for the Recursion lab.
 * 
 * @author YOUR-NAME-HERE
 */
public class BoggleBoard {

    /**
     * Number of rows in the Boggle board.
     */
    private static final int NUM_ROWS = 4;

    /**
     * Number of columns in the Boggle board.
     */
    private static final int NUM_COLS = 4;

    /**
     * The minimum length of a valid word in Boggle.
     */
    private static final int MIN_WORD_LENGTH = 3;

    /**
     * The filename containing a word list.
     */
    private static final String WORD_LIST_FILE = "/Users/ralmeida/Downloads/starter-code-and-test-files/test-files/ospd2.txt";

    /**
     * The Boggle board itself.
     */
    private char[][] board;

    /**
     * The dictionary of all permitted words and valid prefixes
     * to use when finding Boggle words.
     */
    private BoggleDictionary dict;

    /**
     * Set up a new Boggle board by reading in the given board file and
     * construct the Boggle dictionary by reading from the specified word file.
     * 
     * @param boggleFile The filename of the Boggle board file.
     * @param wordFile   The filename of a word list.
     */
    public BoggleBoard(String boggleFile, String wordFile) {
        dict = new BoggleDictionary(wordFile);
        Scanner in;
        try {
            in = new Scanner(new File(boggleFile));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("can't read " + boggleFile);
        }
        board = new char[NUM_ROWS][NUM_COLS];
        for (int i = 0; i < NUM_ROWS; i++) {
            String line = in.nextLine().toLowerCase();
            for (int j = 0; j < NUM_COLS; j++) {
                board[i][j] = line.charAt(j);
            }
        }
    }

    /**
     * Return a textual representation of the Boggle board.
     * 
     * @return The textual board representation.
     */
    @Override
    public String toString() {
        String s = "";
        for (int r = 0; r < NUM_ROWS; r++) {
            for (int c = 0; c < NUM_COLS; c++) {
                s += board[r][c] + " ";
            }
            s += "\n";
        }
        return s.toUpperCase();
    }

    /**
     * Construct and return a set of all possible words that can be found
     * in the Boggle board.
     * 
     * @return A set of all valid words contained in the board.
     */
    public Set<String> findAllWords() {
        Set<String> words = new HashSet<String>();
        for (int r = 0; r < NUM_ROWS; r++) {
            for (int c = 0; c < NUM_COLS; c++) {
                findWords(words, null, r, c); // find every word starting here
            }
        }
        return words;
    }

    /**
     * Primary recursive helper method to use when generating words. Should add all
     * words to the given word set that start with the given prefix and continue on
     * the specified cell.
     * 
     * @param words  The set of all words being collected.
     * @param prefix The current prefix under consideration at this cell.
     * @param r      The row number of the current cell.
     * @param c      The column number of the current cell.
     */
    private void findWords(Set<String> words, List<Integer> cells, int r, int c) {
        if (cells == null) {
            cells = new ArrayList<>();
        }
        int curCell = r * 4 + c;
        cells.add(curCell);
        String prefix = getPrefix(cells);

        if (!dict.isPrefix(prefix)) {
            return;
        } else if (dict.isWord(prefix)) {
            words.add(prefix);
        }

        // getting the coordinates of all neighboring cells
        List<List<Integer>> possibleCoordinates = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int possibleR = r + i;
                int possibleC = c + j;
                possibleCoordinates.add(Arrays.asList(possibleR, possibleC));
            }
        }

        for (List<Integer> possibleCoordinate : possibleCoordinates) {
            int nextR = possibleCoordinate.get(0);
            int nextC = possibleCoordinate.get(1);
            // is the coordinate within the board and the char at at new coordinate was not
            // already visited
            if (isCoordinateValid(nextR, nextC) && cells.indexOf(nextR * 4 + nextC) == -1) {
                findWords(words, new ArrayList<>(cells), nextR, nextC);
            }
        }
    }

    private boolean isCoordinateValid(int r, int c) {
        return (r >= 0 && c >= 0) && (r < NUM_ROWS && c < NUM_COLS);
    }

    private String getPrefix(List<Integer> cells) {
        StringBuilder prefix = new StringBuilder();
        for (int cell : cells) {
            char character = board[cell / 4][cell % 4];
            prefix.append(character);
            if (character == 'q') {
                prefix.append('u');
            }
        }
        return prefix.toString();
    }

    /**
     * Prompt the user for a filename containing a Boggle board, locate all
     * words contained in the board, then print them out.
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Boggle board filename: ");
        String boardFile = scan.next();

        BoggleBoard board = new BoggleBoard(boardFile, WORD_LIST_FILE);
        System.out.println(board);

        Set<String> allWords = board.findAllWords();
        int count = 1;
        for (String word : allWords) {
            System.out.println(count + ". " + word);
            count++;
        }
    }

}
