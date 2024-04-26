package GameOfLife.controller;

import GameOfLife.utils.EStatus;
import GameOfLife.utils.IObserver;
import GameOfLife.utils.ISubject;


/**
 * Controller handles user interactions and propagates changes to the model.
 */

public interface IController extends ISubject, IObserver {

  /**
   * Set the current status of the game
   * @param status The new status to set for the game.
   */
  void setStatus(EStatus status);

  /**
   * Set the size of the game grid.
   * @param size The new size for the grid. This resets the grid to the specified size.
   */
  void setSize(int size);

  /**
   * Set the state of a specific cell in the grid.
   * @param x The x-coordinate of the cell
   * @param y The y-coordinate of the cell
   */
  void setCellStatus(int x, int y);

  /**
   * Set the countdown timer for updates.
   * @param time The time interval in seconds for generation update
   */
  void setTime(int time);

  /**
   * Get the current status of the game.
   * @return The current status of the game
   */
  EStatus getStatus();

  /**
   * Get the current countdown time until the next generation.
   * @return The current countdown time in seconds
   */
  int getCountDown();

  /**
   * Get the current generation number of the game.
   * @return The generation number indicating how many updates have occurred since start
   */
  int getGeneration();

  /**
   * Restart the game
   */
  void restart();

  /**
   * Get the current grid with the state of cells in the game.
   * @return A 2D array representing the game grid, true indicates cell alive
   */
  boolean[][] getGrid();

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
