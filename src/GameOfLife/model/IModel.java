package GameOfLife.model;

import GameOfLife.utils.EStatus;
import GameOfLife.utils.ISubject;

/**
 * This interface outlines the actions that can be performed on the game's model,
 * including game state, grid management, and game progression, etc.
 */
public interface IModel extends ISubject {

  /**
   * Set the countdown time for generation update.
   * @param time The countdown time in seconds
   */
  void setTime(int time);

  /**
   * Set the current game status.
   * @param status The current status (e.g., RUNNING, PAUSE, SEED, etc.).
   */
  void setStatus(EStatus status);

  /**
   * Set the size of the grid.
   * @param size The new size for the grid.
   */
  void setSize(int size);

  /**
   * Set the state of a specific cell.
   * @param x The x-coordinate of the cell
   * @param y The y-coordinate of the cell
   */
  void setCellState(int x, int y);

  /**
   * Get the current status of the game.
   * @return The current status of the game.
   */
  EStatus getStatus();

  /**
   * Restart the game
   */
  void restart();

  /**
   * Get the current generation count.
   * @return The number of generations that have passed in the current game.
   */
  int getGeneration();

  /**
   * Get the current countdown time until the next generation update
   * @return The remaining time in seconds until the next generation update
   */
  int getCountDown();

  /**
   * Get the current grid with the state of cells in the game.
   * @return A 2D array representing the game grid, true indicates cell alive
   */
  boolean[][] getGrid();

  /**
   * Trigger the countdown process, update to the next generation.
   */
  void countDown();

  /**
   * Get the size of the game grid.
   * @return The dimension of the grid
   */
  int getSize();

  /**
   * Check if the initial grid is empty (all cells are dead).
   * @return True if all cells in the grid are dead
   */
  boolean checkInitGridIsEmpty();
}
