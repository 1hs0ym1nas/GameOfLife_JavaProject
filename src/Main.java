import GameOfLife.controller.Controller;
import GameOfLife.controller.IController;
import GameOfLife.model.IModel;
import GameOfLife.model.Model;
import GameOfLife.view.View;

/**
 * Main class to start the application
 */
public class Main {
  public static void main(String[] args) {
    // Model, Controller and View for the MVC model
    IModel model = Model.getModelInstance();
    IController controller = Controller.getControllerInstance(model);
    View view = View.getViewInstance(controller);
  }
}