package GameOfLife.controller;

import GameOfLife.utils.IObserver;


/**
 * As an implementation of both IController and IObserver, this class bridges user interactions
 * and model updates, allowing derived classes to directly influence the model based on user actions
 * and react to changes in the model state.
 */
public abstract class AbstractController implements IController, IObserver {

  /**
   * Notify all attached observers about changes and ensure all observers are informed of
   * changes to the model that the controller manages.
   */
  public abstract void notifyObserver();

  /**
   * Attaches an observer to this controller.
   * It will be implemented by derived classes to add observers that are notified of changes.
   * @param observer The observer to attach to the controller.
   */
  public abstract void attach(IObserver observer);
}
