package GameOfLife.model;
import GameOfLife.utils.EStatus;
import GameOfLife.utils.IObserver;
import GameOfLife.utils.ISubject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Model implements IModel, ISubject {
  private Cell[][] grid;
  private EStatus status;
  private int time;
  private int size;
  private final ArrayList<IObserver> observers = new ArrayList<>();
  private int generation;
  private static IModel instance = null;
  private int countDown = time;
  private final Timer timer;

  private Model() {
    this.status = EStatus.SEED;
    timer = new Timer();
    generation = 0;
    countDown();
  }

  public static IModel getModelInstance() {
    if (instance == null) {
      instance = new Model();
    }
    return instance;
  }

  @Override
  public void setTime(int time) {
    this.time = time;
    this.countDown = time;
  }

  @Override
  public void setStatus(EStatus status) {
    this.status = status;
  }

  @Override
  public void setSize(int size) {
    this.size = size;
    resetGrid();
  }

  /**
   * Reset the game grid to a new empty one with the current size.
   * Each cell in the grid will be dead.
   */
  private void resetGrid() {
    grid = new Cell[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        grid[i][j] = new Cell();
      }
    }
  }

  /**
   * Update the state to the next generation based on the rule.
   * This method calculates the next state for each cell, checks if the game will reach a win or lose
   */
  private void updateNextGeneration() {
    // Get the state of the previous generation
    boolean[][] preGridState = toBooleanGrid();

    // Update the state of the cells for next generation
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        grid[i][j].updateState(i, j, preGridState);
      }
    }

    // Compare previous generation with current generation
    boolean[][] currentGridState = toBooleanGrid();
    checkGameResult(preGridState, currentGridState);
  }

  /**
   * Check the result of the game after updating to the next generation,
   * Update the game status to WIN if the current generation remains the same as the previous grid,
   * or LOSE if there is no cell alive.
   *
   * @param pre The grid state of the previous generation
   * @param cur The current state after the next generation updated
   */
  private void checkGameResult(boolean[][] pre, boolean[][] cur) {
    checkIsLose(cur);
    checkIsWin(pre, cur);
  }

  /**
   * Check if the game should be updated to WIN status
   *
   * @param pre The grid state of the previous generation
   * @param cur The current state after the next generation updated
   */
  private void checkIsWin(boolean[][] pre, boolean[][] cur) {
    // Check if the current generation is the same as the previous generation
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (cur[i][j] != pre[i][j]) {
          return;
        }
      }
    }
    status = EStatus.WIN;
  }

  /**
   * Check if the game should be updated to LOSE status
   *
   * @param cur The current state after the next generation updated
   */
  private void checkIsLose(boolean[][] cur) {
    // Check if there are alive cells in the current generation
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (cur[i][j]) {
          return;
        }
      }
    }
    status = EStatus.LOSE;
  }

  /**
   * Convert the current grid of cells into a 2D boolean array representing status of each cell.
   *
   * @return A 2D array representing the status of a cell (true for alive, false for dead).
   */
  private boolean[][] toBooleanGrid() {
    boolean[][] booleanGrid = new boolean[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        booleanGrid[i][j] = grid[i][j].getState();
      }
    }
    return booleanGrid;
  }

  @Override
  public boolean[][] getGrid() {
    return toBooleanGrid();
  }

  @Override
  public void attach(IObserver observer) {
    observers.add(observer);
  }

  @Override
  public void notifyObserver() {
    for (IObserver observer : observers) {
      observer.update();
    }
  }

  @Override
  public void setCellState(int x, int y) {
    if (x >= 0 && x < size && y >= 0 && y < size) {
      grid[x][y].setState(!grid[x][y].getState());
    }
  }

  @Override
  public EStatus getStatus() {
    return status;
  }


  @Override
  public void restart() {
    status = EStatus.SEED;
    generation = 0;
    countDown = time;
    resetGrid();
    notifyObserver();
  }

  @Override
  public int getGeneration() {
    return generation;
  }

  @Override
  public int getCountDown() {
    return countDown;
  }

  @Override
  public void countDown() {
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        if (status == EStatus.RUNNING) {
          if (countDown == 0) {
            updateNextGeneration();
            countDown = time;
            generation++;
          } else {
            countDown--;
          }
          notifyObserver();
        }
      }
    }, 0, 1000);
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public boolean checkInitGridIsEmpty() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (grid[i][j].getState()) {
          return false;
        }
      }
    }
    return true;
  }



}

