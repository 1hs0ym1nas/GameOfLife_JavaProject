package GameOfLife.Controller;

import GameOfLife.Model.EStatus;
import GameOfLife.utils.IObserver;

public abstract class AbstractController implements IController, IObserver {
  public abstract void setStatus(EStatus status);

  public abstract void setSize(int size);

  public abstract void setCellStatus(int x, int y, boolean state);

  public abstract void setTime(int time);

  public abstract EStatus getStatus();

  public abstract int getCountDown();

  public abstract int getGeneration();

  public abstract void restart();

  public abstract void notifyObserver();

  public abstract void attach(IObserver observer);

  public abstract boolean[][] getGrid();
}
