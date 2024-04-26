package GameOfLife.utils;

/**
 * This interface is a key component in ensuring that changes in the model are communicated to observers,
 * typically views or other components that need to react to state changes.
 */
public interface IObserver {

  /**
   * Called by the model that this observer is attached to whenever a change occurs.
   */
  void update();
}
