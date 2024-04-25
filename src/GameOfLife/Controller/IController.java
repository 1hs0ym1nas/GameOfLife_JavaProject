package GameOfLife.Controller;

import GameOfLife.utils.EStatus;

public interface IController {
  void setStatus(EStatus status);

  void setSize(int size);

  void setCellStatus(int x, int y);

  void setTime(int time);

  EStatus getStatus();

  int getCountDown();

  int getGeneration();

  void restart();

  boolean[][] getGrid();

  int getSize();
  boolean checkInitGridIsEmpty();
}
