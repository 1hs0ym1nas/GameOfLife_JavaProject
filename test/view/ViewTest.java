package view;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import GameOfLife.controller.AbstractController;
import GameOfLife.controller.Controller;
import GameOfLife.model.IModel;
import GameOfLife.model.Model;
import GameOfLife.utils.EStatus;
import GameOfLife.view.View;
import java.lang.reflect.Field;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import org.junit.Before;
import org.junit.Test;

public class ViewTest {
  private IModel model;
  private AbstractController controller;
  private JButton startButton;
  private JButton pauseButton;
  private JButton restartButton;
  private JSpinner sizeSpinner;
  private JSpinner timeSpinner;
  private JLabel currentGeneration;
  private JLabel countDown;

  @Before
  public void setUp() throws NoSuchFieldException, IllegalAccessException {
    // Model, Controller and View for the MVC model
    model = Model.getModelInstance();
    controller = Controller.getControllerInstance(model);
    View view = View.getViewInstance(controller);

    // Use reflection to access private fields
    Field field = View.class.getDeclaredField("startButton");
    field.setAccessible(true);
    startButton = (JButton) field.get(view);

    field = View.class.getDeclaredField("pauseButton");
    field.setAccessible(true);
    pauseButton = (JButton) field.get(view);

    field = View.class.getDeclaredField("restartButton");
    field.setAccessible(true);
    restartButton = (JButton) field.get(view);

    field = View.class.getDeclaredField("timeSpinner");
    field.setAccessible(true);
    timeSpinner = (JSpinner) field.get(view);

    field = View.class.getDeclaredField("sizeSpinner");
    field.setAccessible(true);
    sizeSpinner = (JSpinner) field.get(view);

    field = View.class.getDeclaredField("currentGeneration");
    field.setAccessible(true);
    currentGeneration = (JLabel) field.get(view);

    field = View.class.getDeclaredField("countDown");
    field.setAccessible(true);
    countDown = (JLabel) field.get(view);
  }

  /**
   * Test the buttons in the view.
   */
  @Test
  public void testButtons() {
    // Test the text of buttons
    assertEquals("Start", startButton.getText());
    assertEquals("Pause", pauseButton.getText());
    assertEquals("Restart", restartButton.getText());

    // Test the enabled status of buttons
    assertEquals(EStatus.SEED, model.getStatus());
    assertTrue(startButton.isEnabled());
    assertFalse(pauseButton.isEnabled());
    assertTrue(restartButton.isEnabled());

    // Test the action of restart button
    restartButton.doClick();
    assertEquals(EStatus.SEED, model.getStatus());

    // Test the action of the start button
    startButton.doClick();
    assertTrue(model.checkInitGridIsEmpty());
    assertEquals(EStatus.SEED, model.getStatus());
    model.setCellState(1, 1);
    startButton.doClick();
    assertFalse(model.checkInitGridIsEmpty());
    assertEquals(EStatus.RUNNING, model.getStatus());

    // Test the enabled status of buttons
    assertFalse(startButton.isEnabled());
    assertTrue(pauseButton.isEnabled());
    assertFalse(restartButton.isEnabled());

    // Test the action of pause button
    assertTrue(pauseButton.isEnabled());
    pauseButton.doClick();
    assertEquals(EStatus.PAUSE, model.getStatus());

    // Test the enabled status of buttons
    assertTrue(startButton.isEnabled());
    assertFalse(pauseButton.isEnabled());
    assertTrue(restartButton.isEnabled());

    // Test the action of start button
    startButton.doClick();
    assertEquals(EStatus.RUNNING, model.getStatus());
    pauseButton.doClick();

    // Test the action of restart button
    restartButton.doClick();
    assertEquals(EStatus.SEED, model.getStatus());
    assertTrue(model.checkInitGridIsEmpty());
  }

  /**
   * Test the spinner in the view.
   */
  @Test
  public void testSpinner() {
    // Test the value of spinners
    assertEquals(3, timeSpinner.getValue());
    assertEquals(10, sizeSpinner.getValue());

    // Test the action of spinners
    assertEquals(10, model.getSize());
    assertEquals(3, model.getCountDown());
    timeSpinner.setValue(5);
    sizeSpinner.setValue(20);
    assertEquals(20, model.getSize());
    assertEquals(5, model.getCountDown());

    // Test enable status of spinners
    model.setCellState(1, 1);
    startButton.doClick();
    assertFalse(timeSpinner.isEnabled());
    assertFalse(sizeSpinner.isEnabled());
    pauseButton.doClick();
    assertFalse(timeSpinner.isEnabled());
    assertFalse(sizeSpinner.isEnabled());
  }

  /**
   * Test the label in the view.
   * @throws InterruptedException when the thread is interrupted
   */
  @Test
  public void testLabel() throws InterruptedException {
    // Test the text of labels
    assertEquals("0", currentGeneration.getText());
    assertEquals("3", countDown.getText());
    controller.setTime(5);
    assertEquals("5", countDown.getText());
    model.setCellState(1, 1);
    model.setCellState(1, 3);
    startButton.doClick();
    controller.setTime(3);
    sleep(4000);
    assertEquals("1", currentGeneration.getText());
    sleep(4000);
    assertEquals("2", currentGeneration.getText());
    pauseButton.doClick();
    restartButton.doClick();
    assertEquals("0", currentGeneration.getText());
  }
}
