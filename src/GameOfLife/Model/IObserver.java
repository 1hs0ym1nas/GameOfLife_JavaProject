package GameOfLife.Model;

public interface IObserver {
  void update(boolean[][] grid, int generation);
}
