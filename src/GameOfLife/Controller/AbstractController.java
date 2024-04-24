package GameOfLife.Controller;

import GameOfLife.utils.IObserver;

public abstract class AbstractController implements IController, IObserver {

  public abstract void notifyObserver();

  public abstract void attach(IObserver observer);
}
