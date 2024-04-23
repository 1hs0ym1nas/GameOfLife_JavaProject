package GameOfLife.Model;

public interface IModel extends ISubject {
  void setTime(int time);
  void setStatus(EStatus status);
  void setSize(int size);
  void setNextGeneration();
  void setCellState(int x, int y, boolean state);

}
