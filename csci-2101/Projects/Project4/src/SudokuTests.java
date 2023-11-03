import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Test;

/**
 * Test cases used during development of the sudoku solver.
 */
public class SudokuTests {

        // I do not know why the tests cannot access by relative path so I created this
        // ABSOLUTE_PATH variable to make the code less ugly
        /** Absolute path to project folder in my machine */
        static final private String ABSOLUTE_PATH = "/Users/ralmeida/scripts/bowdoin-classes/csci-2101/Project4/";

        @Test
        public void isValidMove() throws FileNotFoundException {
                SudokuPuzzle test = new SudokuPuzzle(
                                ABSOLUTE_PATH + "puzzles/s-valid-move-test.txt");

                // invalid moves
                assertEquals(false, test.isValidMove(new SudokuMove(1, 0, 0))); // is empty
                assertEquals(false, test.isValidMove(new SudokuMove(2, 3, 6))); // row
                assertEquals(false, test.isValidMove(new SudokuMove(3, 2, 3))); // col
                assertEquals(false, test.isValidMove(new SudokuMove(4, 3, 3))); // box

                // somewhat arbitrary, but why not include...
                // valid moves
                assertEquals(true, test.isValidMove(new SudokuMove(2, 1, 1))); // is empty
                assertEquals(true, test.isValidMove(new SudokuMove(3, 7, 2))); // row
                assertEquals(true, test.isValidMove(new SudokuMove(4, 7, 5))); // col
                assertEquals(true, test.isValidMove(new SudokuMove(5, 4, 2))); // col
        }

        @Test
        public void isSolved() throws FileNotFoundException {
                SudokuPuzzle test = new SudokuPuzzle(
                                ABSOLUTE_PATH + "puzzles/s1-solution.txt");
                assertEquals(true, test.isSolved());
        }

        @Test
        public void equals() throws FileNotFoundException {
                SudokuPuzzle test = new SudokuPuzzle(
                                ABSOLUTE_PATH + "puzzles/s1.txt");
                SudokuPuzzle test2 = new SudokuPuzzle(
                                ABSOLUTE_PATH + "puzzles/s1.txt");
                SudokuPuzzle test3 = new SudokuPuzzle(
                                ABSOLUTE_PATH + "puzzles/s2.txt");

                assertEquals(true, test.equals(test2));
                assertEquals(true, test2.equals(test));
                assertEquals(false, test.equals(test3));
        }

        @Test
        public void solve() throws FileNotFoundException {
                SudokuPuzzle test = new SudokuPuzzle(
                                ABSOLUTE_PATH + "puzzles/s1.txt");
                SudokuPuzzle testSolution = new SudokuPuzzle(
                                ABSOLUTE_PATH + "puzzles/s1-solution.txt");

                SudokuPuzzle test2 = new SudokuPuzzle(
                                ABSOLUTE_PATH + "puzzles/s2.txt");
                SudokuPuzzle testSolution2 = new SudokuPuzzle(
                                ABSOLUTE_PATH + "puzzles/s2-solution.txt");

                SudokuSolver solver = new SudokuSolver();
                solver.solve(test);
                solver.solve(test2);

                assertEquals(testSolution.toString(), test.toString());
                assertEquals(testSolution2.toString(), test2.toString());
        }
}