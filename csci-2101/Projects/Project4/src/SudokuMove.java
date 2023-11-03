/**
 * Represents a 'move' that can be applied to a Sudoku puzzle board. When the
 * moves are created, they are not necessarily valid
 */
public class SudokuMove {
   /** The actual number that will be written in the sudoku puzzle cell */
   private int value;
   /** The index of the row of the cell */
   private int row;
   /** The index of the col of the cell */
   private int col;

   public SudokuMove(int value, int row, int col) {
      this.value = value;
      this.row = row;
      this.col = col;
   }

   public int getValue() {
      return value;
   }

   public int getRow() {
      return row;
   }

   public int getCol() {
      return col;
   }
}
