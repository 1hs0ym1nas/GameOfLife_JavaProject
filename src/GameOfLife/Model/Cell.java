package GameOfLife.Model;

public class Cell implements ICell {
  private boolean state;
  private boolean nextState;

  public Cell() {
    this.state = false; // By default, the cell is dead
    this.nextState = false;
  }

  @Override
  public void setState(boolean newState) {
    this.state = newState;
    this.nextState = newState;
  }

  @Override
  public int countNeighboring(boolean[][] grid, int x, int y) {
    int count = 0;
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (i == 0 && j == 0) continue; // Skip the cell itself
        int dx = x + i;
        int dy = y + j;
        if (dx >= 0 && dx < grid.length && dy >= 0 && dy < grid[dx].length && grid[dx][dy]) {
          count++;
        }
      }
    }
    return count;
  }

  @Override
  public void setAlive(int neighbors) {
    nextState = (state && (neighbors == 2 || neighbors == 3)) || (!state && neighbors == 3);
  }

  @Override
  public void updateState() {
    state = nextState;
  }

  @Override
  public boolean getState() {
    return state;
  }
}
