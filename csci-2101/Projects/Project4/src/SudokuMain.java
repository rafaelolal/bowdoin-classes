import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Rafael Almeida
 * CSCI 2101 C
 * 10/18/2023
 * Project 4: Super Sudoku Solver
 * 
 * Handles receiving input of puzzle, solution, building the puzzle data
 * structure, solving it, and comparing to solution, if applicable
 * 
 */
public class SudokuMain {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);

        // taking in input of file names
        System.out.print("Enter filename of puzzle: ");
        String puzzleFilename = scan.nextLine();
        System.out.print("Enter filename of solution (optional): ");
        String solutionFilename = scan.nextLine();

        // building puzzle data structure
        SudokuPuzzle puzzle = new SudokuPuzzle(
                puzzleFilename);

        System.out.println("\nStarting puzzle:");
        System.out.println(puzzle);

        SudokuSolver solver = new SudokuSolver();
        solver.solve(puzzle);

        System.out.println("Solved puzzle:");
        System.out.println(puzzle);

        // build solution puzzle and compare to solved puzzle only if a solution file
        // was provided
        if (!solutionFilename.equals("")) {
            SudokuPuzzle solution = new SudokuPuzzle(solutionFilename);
            System.out.println(puzzle.equals(solution) ? "Solution is correct!" : "Solution is incorrect!");
        }

        scan.close();
    }
}
