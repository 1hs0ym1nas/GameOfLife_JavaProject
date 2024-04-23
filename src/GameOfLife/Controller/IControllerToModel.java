package GameOfLife.Controller;
import GameOfLife.Model.EStatus;

public interface IControllerToModel {
  void setStatus(EStatus status);
  void setSize(int size);
  void setCellState(int x, int y, boolean state);
  void setTime(int time);
}
