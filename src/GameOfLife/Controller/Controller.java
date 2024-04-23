package GameOfLife.Controller;

import GameOfLife.Model.EStatus;
import GameOfLife.Model.IModel;
import GameOfLife.View.IView;

public class Controller implements IController {
  private IModel model;
  private IView view;

  public Controller(IModel model, IView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public IControllerToModel getControllerToModel() {
    return new ControllerToModel();
  }

  @Override
  public IControllerToView getControllerToView() {
    return new ControllerToView();
  }

  private class ControllerToModel implements IControllerToModel {
    @Override
    public void setStatus(EStatus status) {
      model.setStatus(status);
    }

    @Override
    public void setSize(int size) {
      model.setSize(size);
    }

    @Override
    public void setCellState(int x, int y, boolean state) {
      model.setCellState(x, y, state);
    }

    @Override
    public void setTime(int time) {
      model.setTime(time);
    }
  }

  private class ControllerToView implements IControllerToView {
    @Override
    public void setStatus(EStatus status) {
      view.setStatus(status);
    }

    @Override
    public void setSize(int size) {
      view.setSize(size);
    }

    @Override
    public void setCellState(int x, int y, boolean state) {
      view.setCellState(x, y, state);
    }

    @Override
    public void setTime(int time) {
      view.setTime(time);
    }
  }

}

