package GameOfLife.view;

import GameOfLife.utils.EStatus;

public interface IView {
  void setTime(int time);

  void setCellState(int x, int y, boolean state);

  void setSize(int size);

  void setStatus(EStatus status);
}
