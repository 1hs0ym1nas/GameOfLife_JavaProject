package GameOfLife.utils;

/**
 * This interface defines the fundamental methods that a subject must implement
 * to manage and notify its observers.
 */
public interface ISubject {

  /**
   * Notify all attached observers of a change in the subject's state.
   */
  void notifyObserver();

  /**
   * Attach an observer to the subject.
   * This method registers an observer to be notified of changes in the subject's state.
   * @param observer The observer that will be attached to the subject.
   */
  void attach(IObserver observer);
}
