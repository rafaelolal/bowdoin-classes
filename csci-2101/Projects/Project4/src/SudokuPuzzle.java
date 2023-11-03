import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a conventional Sudoku puzzle board, 9x9 and divided into 9 3x3
 * boxes
 */
public class SudokuPuzzle {
    // the following variables help avoiding magic numbers
    /** How many rows the puzzle board has */
    static final public int ROW_COUNT = 9;
    /** How many cols the puzzle board has */
    static final public int COL_COUNT = 9;
    /** The maximum value that can be written in a puzzle cell */
    static final public int MAX_VALUE = 9;
    /** The dimensions of the 9 3x3 boxes the puzzle is divided private into */
    static final private int BOX_DIMENSION = 3;
    /** How many vertical dividers the string representation of the board has */
    static final private int DIVIDER_COUNT = 4;
    /**
     * The final width of the string representation of the board, used to know how
     * many dashes to print
     */
    // cols are multiplied by two because each character has a space after it, all
    // dividers have a space after them except the last one
    static final private int GRID_WIDTH = COL_COUNT * 2 + DIVIDER_COUNT * 2 - 1;

    /**
     * A 2D array that is used to represent the rows and values of the board,
     * storing a value of zero means the cell is empty
     */
    private int[][] grid;

    public SudokuPuzzle(String fileName) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(fileName));
        grid = new int[ROW_COUNT][COL_COUNT];

        // scanning all integers in the file with an iterative value representing their
        // appearance and using the utility methods to figure out the row and col index
        int i = 0;
        while (scan.hasNextInt()) {
            grid[Utility.toRow(i)][Utility.toCol(i)] = scan.nextInt();
            i++;
        }
    }

    /**
     * Returns all 'empty' cells in the grid, all cells that can have a move made to
     * them, cells that have the value of 0
     * 
     * @return emptyCells all cells holding a value of zero
     */
    public ArrayList<Integer> getEmptyCells() {
        ArrayList<Integer> emptyCells = new ArrayList<>();
        for (int i = 0; i < ROW_COUNT * COL_COUNT; i++) {
            if (grid[Utility.toRow(i)][Utility.toCol(i)] == 0) {
                emptyCells.add(i);
            }
        }

        return emptyCells;
    }

    /**
     * Given a valid SudokuMove object, it will apply that move to the board by
     * overwriting the value with move's value in the appropriate coordinate
     * 
     * @param move the move to be applied
     */
    public void makeMove(SudokuMove move) {
        grid[move.getRow()][move.getCol()] = move.getValue();
    }

    /**
     * Given a valid SudokuMove object that was previously applied, reset the value
     * at that coordinate to 0
     * 
     * @param move the move with the coordinates to be reset to 0
     */
    public void undoMove(SudokuMove move) {
        grid[move.getRow()][move.getCol()] = 0;
    }

    /**
     * Given a SudokuMove object, checks if that move could be applied to the board.
     * Checks if the cell at the coordinate is empty, then if the value is on the
     * row and then the col the move would be applied in, and checks all values in
     * the same box as the potential move
     * 
     * @param move the move to be tested for validity
     * 
     * @return if the move is valid or not
     */
    public boolean isValidMove(SudokuMove move) {
        // is empty
        if (grid[move.getRow()][move.getCol()] != 0) {
            return false;
        }

        // row
        for (int val : grid[move.getRow()]) {
            if (val == move.getValue()) {
                return false;
            }
        }

        // col
        for (int i = 0; i < ROW_COUNT; i++) {
            if (grid[i][move.getCol()] == move.getValue()) {
                return false;
            }
        }

        // box
        // dividing the move's coordinates by three to figure out the index of the row
        // and col of the box it is in. Then multiplying that by three to convert back
        // to the index of the cell at the top left of the box
        int startingRow = move.getRow() / BOX_DIMENSION * BOX_DIMENSION;
        int startingCol = move.getCol() / BOX_DIMENSION * BOX_DIMENSION;
        // looping through all values in the box of the move starting at the top left
        for (int i = 0; i < BOX_DIMENSION; i++) {
            for (int j = 0; j < BOX_DIMENSION; j++) {
                if (grid[startingRow + i][startingCol + j] == move.getValue()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if the puzzle is solved by verifying that none of the cells are empty
     * by checking if any have their value as equal to 0
     * 
     * @return if the puzzle is solved or not
     */
    public boolean isSolved() {
        for (int[] row : grid) {
            for (int val : row) {
                if (val == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a puzzle is equal to another puzzle object by comparing if their
     * string representations are identical
     * 
     * @param other the puzzle that the current instance should be compared to
     * 
     * @return if the puzzles are equal or not
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof SudokuPuzzle) {
            return toString().equals(other.toString());
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("-".repeat(GRID_WIDTH) + "\n");

        for (int i = 0; i < ROW_COUNT; i++) {
            str.append("| ");

            for (int j = 0; j < COL_COUNT; j++) {
                int val = grid[i][j];
                str.append((val == 0 ? "_" : val) + " ");

                if ((j + 1) % BOX_DIMENSION == 0) {
                    str.append("| ");
                }
            }

            str.append("\n");

            if ((i + 1) % BOX_DIMENSION == 0) {
                str.append("-".repeat(GRID_WIDTH) + "\n");
            }
        }

        return str.toString();
    }
}