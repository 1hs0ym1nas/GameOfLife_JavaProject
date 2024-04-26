package GameOfLife.model;

/**
 * This interface provides methods to manage and update the state of a cell based on the primary rule of the game,
 * where each cell's next state is determined by the number of alive neighbors it has.
 */
public interface ICell {

  /**
   * Set the state of this cell.
   * @param state The new state of the cell, where true represents alive and false represents dead.
   */
  void setState(boolean state);

  /**
   * Get the current state of this cell.
   * @return The current state of the cell; true if the cell is alive, false if it is dead.
   */
  boolean getState();

  /**
   * Update the state of the current cell based on the number and states of its neighbors.
   * The cell's new state depends on the number of adjacent live cells.
   *
   * @param x The x-coordinate of this cell
   * @param y The y-coordinate of this cell
   * @param grid A 2D array representing the current states of all cells.
   */
  void updateState(int x, int y, boolean[][] grid);
}
