package GameOfLife.Model;

public interface ISubject {
  void notifyObserver();
  void attach(IObserver observer);
}
