package GameOfLife.utils;

public interface ISubject {
  void notifyObserver();
  void attach(IObserver observer);
}
