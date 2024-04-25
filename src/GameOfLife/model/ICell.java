package GameOfLife.model;

public interface ICell {
  void setState(boolean state);
  boolean getState();
  void updateState(int x, int y, boolean[][] grid);
}
