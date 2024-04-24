package GameOfLife.Model;

import GameOfLife.utils.ISubject;

public abstract class AbstractModel implements IModel, ISubject {
  public abstract void setTime(int time);
  public abstract void setStatus(EStatus status);
  public abstract void setSize(int size);
  public abstract void setCellState(int x, int y, boolean state);
  public abstract EStatus getStatus();
  public abstract void restart();
  public abstract int getGeneration();
  public abstract int getCountDown();
  public abstract boolean[][] getGrid();
  public abstract void countDown();
}
