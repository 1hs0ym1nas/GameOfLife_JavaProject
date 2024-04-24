package GameOfLife.Controller;

import GameOfLife.Model.EStatus;

public interface IController {
  void setStatus(EStatus status) throws Exception;

  void setSize(int size) throws Exception;

  void setCellStatus(int x, int y, boolean state) throws Exception;

  void setTime(int time) throws Exception;

  EStatus getStatus();

  int getCountDown();

  int getGeneration();
}
