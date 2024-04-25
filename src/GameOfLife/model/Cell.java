package GameOfLife.model;

public class Cell implements ICell {
  private boolean state;

  public Cell() {
    this.state = false; // By default, the cell is dead
  }

  @Override
  public void setState(boolean newState) {
    this.state = newState;
  }

  @Override
  public void updateState(int x, int y, boolean[][] grid) {
    int neighbors = 0;

    // Coordinates of the neighbors
    int[] neighborsXs = {x + 1, x , x - 1};
    int[] neighborsYs = {y + 1, y , y - 1};

    // Find the number of neighbors based on the coordinates
    for (int neighborsX : neighborsXs) {
      for (int neighborsY : neighborsYs) {
        if ((neighborsX >= 0 && neighborsX < grid.length) // X is in bounds
            && (neighborsY >= 0 && neighborsY < grid[neighborsX].length) // Y is in bounds
            && grid[neighborsX][neighborsY] // Cell is alive
            && (neighborsX != x && neighborsY != y) // Cell is not itself
        ) {
          neighbors++;
        }
      }
    }

    state = neighbors == 3 || neighbors == 2;
  }

  @Override
  public boolean getState() {
    return state;
  }
}
