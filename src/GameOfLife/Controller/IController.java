package GameOfLife.Controller;

public interface IController {
  IControllerToModel getControllerToModel();
  IControllerToView getControllerToView();
}
