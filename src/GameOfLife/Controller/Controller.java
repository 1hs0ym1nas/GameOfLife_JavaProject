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
    this.model = Model.getModelInstance();
    this.observers = new ArrayList<>();
    model.attach(this);
  }

  public static AbstractController getControllerInstance() {
    return new Controller();
  }

  @Override
  public void setStatus(EStatus status) {
    model.setStatus(status);
    notifyObserver();
  }

  @Override
  public void setCellStatus(int x, int y) {
    model.setCellState(x, y);
    notifyObserver();
  }

  @Override
  public void setSize(int size) {
    model.setSize(size);
    notifyObserver();
  }

  @Override
  public void setTime(int time) {
    model.setTime(time);
  }

  @Override
  public EStatus getStatus() {
    return model.getStatus();
  }

  @Override
  public int getCountDown() {
    return model.getCountDown();
  }

  @Override
  public int getGeneration() {
    return model.getGeneration();
  }

  @Override
  public void restart() {
    model.restart();
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
    notifyObserver();
  }

  @Override
  public boolean[][] getGrid() {
    return model.getGrid();
  }

  @Override
  public int getSize() {
    return model.getSize();
  }
}

