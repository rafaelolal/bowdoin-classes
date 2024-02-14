/**
 * Utility class with common methods used throughout the program.
 */
public final class Utility {
    private Utility() {
    }

    /**
     * Given an integer that represents the index of a cell with zero being the top
     * left, returns the index of the row it is in
     */
    public static int toRow(int index) {
        return index / SudokuPuzzle.ROW_COUNT;
    }

    /**
     * Given an integer that represents the index of a cell with zero being the top
     * left, returns the index of the col it is in
     * 
     * @param index an index
     */
    public static int toCol(int index) {
        return index % SudokuPuzzle.COL_COUNT;
    }
}
