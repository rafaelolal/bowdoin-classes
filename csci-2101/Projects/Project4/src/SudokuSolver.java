import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Includes a solver method that uses a greedy and backtracking approach to pick
 * the locally optimal value to input into a valid empty cell
 */
public class SudokuSolver {

    /** The sequence of moves made to solve the puzzle */
    private Deque<SudokuMove> moves;

    public SudokuSolver() {
        moves = new ArrayDeque<>();
    }

    /**
     * Solves a sudoku puzzle by repeatedly trying values until the puzzle is
     * solved. It will iterate forwards and backwards over the cells that were
     * initially empty in the puzzle and attempt moves. If a move leads to not being
     * able to make a move in the subsequent cell, then it will backtrack by
     * iterating backwards in the empty cells, undoing the move from the board, and
     * attempting a value that was not attempted
     */
    public void solve(SudokuPuzzle puzzle) {
        ArrayList<Integer> emptyCells = puzzle.getEmptyCells();

        int i = 0;
        int startingVal = 1;
        while (!puzzle.isSolved()) {
            boolean moveMade = false;

            // attempting to make a move
            for (int val = startingVal; val <= SudokuPuzzle.MAX_VALUE; val++) {
                int currentCell = emptyCells.get(i);

                // converting empty cell index to a SudokuMove object
                SudokuMove move = new SudokuMove(val,
                        Utility.toRow(currentCell),
                        Utility.toCol(currentCell));

                // checking if the move is valid
                if (puzzle.isValidMove(move)) {
                    moves.push(move); // adding to moves made
                    puzzle.makeMove(move); // making the move
                    moveMade = true;
                    startingVal = 1; // resetting the startingVal for the next empty cell
                    i++; // iterating over to the next empty cell
                    break;
                }
            }

            // after all moves were attempted and no move was made
            if (!moveMade) {
                SudokuMove lastMove = moves.pop(); // removing the previously made move
                puzzle.undoMove(lastMove); // undoing the previously made move from the board
                startingVal = lastMove.getValue() + 1; // since I will be attempting to make a new move on a cell I
                                                       // tried already, I want to make sure I do not try the same value
                i--; // iterating backwards to the previously cell that was a bad move
            }
        }
    }
}
