package GameOfLife.Controller;

import GameOfLife.Model.EStatus;
import GameOfLife.Model.IModel;
import GameOfLife.Model.Model;
import GameOfLife.utils.IObserver;
import java.util.ArrayList;

public class Controller extends AbstractController {
  private final IModel model;
  private final ArrayList<IObserver> observers;

  private Controller() {
    this.model = new Model(10);
    this.observers = new ArrayList<>();
  }

  public static AbstractController getControllerInstance() {
    return new Controller();
  }

  @Override
  public void setStatus(EStatus status) throws Exception {
    model.setStatus(status);
    notifyObserver();
  }

  @Override
  public void setCellStatus(int x, int y, boolean state) throws Exception {
    model.setCellState(x, y, state);
  }

  @Override
  public void setSize(int size) throws Exception {
    model.setSize(size);
  }

  @Override
  public void setTime(int time) throws Exception {
    model.setTime(time);
  }

  @Override
  public EStatus getStatus() {
    return model.getStatus();
  }

  @Override
  public int getCountDown() {
    return 0;
  }

  @Override
  public int getGeneration() {
    return 0;
  }

  @Override
  public void notifyObserver() {
    for (IObserver observer : observers) {
      observer.update();
    }
  }

  @Override
  public void attach(IObserver observer) {
    this.observers.add(observer);
  }

  @Override
  public void update() {

  }
}

