package GameOfLife.Model;
import java.util.ArrayList;
import java.util.List;

public class Model implements IModel {
  private Cell[][] grid;
  private EStatus status;
  private int time;
  private int size;
  private final List<IObserver> observers = new ArrayList<>();

  public Model(int size) {
    this.size = size;
    this.grid = new Cell[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        grid[i][j] = new Cell();
      }
    }
    this.status = EStatus.SEED;
  }

  @Override
  public void setTime(int time) {
    this.time = time;
  }

  @Override
  public void setStatus(EStatus status) {
    this.status = status;
  }

  @Override
  public void setSize(int size) {
    this.size = size;
    grid = new Cell[size][size];
    // Initialize cells
  }

  @Override
  public void setNextGeneration() {
    // Compute next generation
    boolean[][] nextGridState = new boolean[size][size];
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        int neighbors = grid[i][j].countNeighboring(this.toBooleanGrid(), i, j);
        grid[i][j].setAlive(neighbors);
        nextGridState[i][j] = grid[i][j].getState();
      }
    }

    // Update grid state to the next generation
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        grid[i][j].updateState();
      }
    }

    notifyObserver();
  }

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
  public void attach(IObserver observer) {
    observers.add(observer);
  }

  @Override
  public void notifyObserver() {
    for (IObserver observer : observers) {
      observer.update(this.toBooleanGrid(), time);
    }
  }

  @Override
  public void setCellState(int x, int y, boolean state) {
    if (x >= 0 && x < size && y >= 0 && y < size) {
      grid[x][y].setState(state);
    }
  }

  @Override
  public EStatus getStatus() {
    return status;
  }
}

