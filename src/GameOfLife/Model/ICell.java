package GameOfLife.Model;

public interface ICell {
  int countNeighboring(boolean[][] grid, int x, int y);
  void setAlive(int neighbors);
  void updateState();
  boolean getState();
  void setState(boolean state);
}
