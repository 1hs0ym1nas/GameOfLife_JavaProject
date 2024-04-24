package GameOfLife.Controller;

import GameOfLife.Model.EStatus;

public interface IController {
  void setStatus(EStatus status);

  void setSize(int size);

  void setCellStatus(int x, int y, boolean state);

  void setTime(int time);

  EStatus getStatus();

  int getCountDown();

  int getGeneration();

  void restart();
}
