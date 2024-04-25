package controller;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import GameOfLife.controller.AbstractController;
import GameOfLife.controller.Controller;
import GameOfLife.model.IModel;
import GameOfLife.model.Model;
import GameOfLife.utils.EStatus;
import org.junit.Before;
import org.junit.Test;

public class ControllerTest {
  private IModel model;
  private AbstractController controller;

  @Before
  public void setUp() {
    // Model, Controller and View for the MVC model
    model = Model.getModelInstance();
    controller = Controller.getControllerInstance(model);
  }

  /**
   * Test the setStatus and getStatus method.
   */
  @Test
  public void testStatus() {
    assertEquals(EStatus.SEED, controller.getStatus());
    assertEquals(EStatus.SEED, model.getStatus());
    controller.setStatus(EStatus.RUNNING);
    assertEquals(EStatus.RUNNING, controller.getStatus());
    assertEquals(EStatus.RUNNING, model.getStatus());
    controller.setStatus(EStatus.PAUSE);
    assertEquals(EStatus.PAUSE, controller.getStatus());
    assertEquals(EStatus.PAUSE, model.getStatus());
    controller.restart();
  }

  /**
   * Test the setSize and getSize method.
   */
  @Test
  public void testSize() {
    controller.setSize(10);
    assertEquals(10, controller.getSize());
    assertEquals(10, model.getSize());
    controller.setSize(2);
    assertEquals(2, controller.getSize());
    assertEquals(2, model.getSize());
    controller.restart();
  }

  /**
   * Test the setTime and getCountDown method.
   */
  @Test
  public void testTIme() {
    controller.setTime(10);
    assertEquals(10, controller.getCountDown());
    assertEquals(10, model.getCountDown());
    controller.setTime(2);
    assertEquals(2, controller.getCountDown());
    assertEquals(2, model.getCountDown());
    controller.restart();
  }

  /**
   * Test the setCellStatus and getGrid method.
   */
  @Test
  public void testCell() {
    controller.setSize(10);
    controller.setCellStatus(0, 0);
    assertTrue(model.getGrid()[0][0]);
    assertFalse(model.getGrid()[0][1]);
    controller.setCellStatus(0, 0);
    assertFalse(model.getGrid()[0][0]);
    controller.restart();
  }

  /**
   * Test the getGeneration method.
   *
   * @throws InterruptedException when the thread is interrupted
   */
  @Test
  public void testRestart() throws InterruptedException {
    controller.setSize(10);
    controller.setTime(1);
    controller.setCellStatus(0, 0);
    controller.setCellStatus(0, 1);
    controller.setCellStatus(0, 2);
    controller.setStatus(EStatus.RUNNING);
    sleep(2000);
    assertEquals(1, model.getGeneration());
    controller.restart();
    assertEquals(0, model.getGeneration());
    assertEquals(1, model.getCountDown());
    assertEquals(EStatus.SEED, model.getStatus());
    controller.restart();
  }

  /**
   * Test the checkInitGridIsEmpty method.
   */
  @Test
  public void testCheckInitGridIsEmpyty() {
    controller.setSize(10);
    assertTrue(model.checkInitGridIsEmpty());
    controller.setCellStatus(0, 0);
    assertFalse(model.checkInitGridIsEmpty());
    controller.restart();
  }
}
