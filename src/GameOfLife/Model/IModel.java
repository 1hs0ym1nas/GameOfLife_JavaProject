package GameOfLife.Model;

import GameOfLife.utils.EStatus;
import GameOfLife.utils.ISubject;

public interface IModel extends ISubject {
  void setTime(int time);
  void setStatus(EStatus status);
  void setSize(int size);
  void setCellState(int x, int y);
  EStatus getStatus();
  void restart();
  int getGeneration();
  int getCountDown();
  boolean[][] getGrid();
  void countDown();
  int getSize();
  boolean checkInitGridIsEmpty();
}
