package GameOfLife.utils;

import GameOfLife.Model.IObserver;

public interface ISubject {
  void notifyObserver();
  void attach(IObserver observer);

  void attach(GameOfLife.utils.IObserver observer);
}
