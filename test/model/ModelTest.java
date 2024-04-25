package model;

import GameOfLife.model.Model;
import GameOfLife.utils.EStatus;
import GameOfLife.utils.IObserver;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test suite for testing the Model class in the Game of Life implementation.
 * This suite covers singleton behavior, state management, observer notification,
 * and game mechanics such as cell state transitions and win/lose conditions.
 */

public class ModelTest {
  private Model model;

  @Before
  public void setUp(){
    model = (Model) Model.getModelInstance();
    model.setSize(3);
    model.setTime(10);
  }

  /**
   * Check if the initial state of the model is SEED as the requirement.
   */
  @Test
  public void testInitialStatus() {
    assertEquals("Initial status should be SEED", EStatus.SEED, model.getStatus());
  }

  /**
   * Test the ability to reset time.
   */
  @Test
  public void testSetTime() {
    model.setTime(5);
    assertEquals("Time should be updated to 5s", 5, model.getCountDown());
    model.restart();
  }

  /**
   * Ensure resizing model will reset the grid and reinitialize it.
   */
  @Test
  public void testSetSizeResetsGrid() {
    model.setSize(5);
    assertTrue("Grid should be empty after size reset", model.checkInitGridIsEmpty());
    assertEquals("Grid size should be updated to 5x5", 5, model.getSize());
    model.restart();
  }

  /**
   * Test setting the model's status.
   */
  @Test
  public void testSetStatus() {
    model.setStatus(EStatus.RUNNING);
    assertEquals("Status should be RUNNING", EStatus.RUNNING, model.getStatus());
    model.restart();
  }

  /**
   * Helper class to test observer notifications. Implements IObserver and records whether an update has occurred.
   */
  static class TestObserver implements IObserver {
    private boolean notified = false;

    @Override
    public void update() {
      notified = true;
    }

    public boolean isNotified() {
      return notified;
    }
  }

  /**
   * Verify that observer is notified of changes in the model.
   */
  @Test
  public void testNotifyObserver() {
    TestObserver testObserver = new TestObserver();
    model.attach(testObserver);
    model.notifyObserver();
    assertTrue("Observer should have been notified", testObserver.isNotified());
    model.restart();
  }

  /**
   * Tests toggling the state of a cell in the grid.
   */
  @Test
  public void testSetCellState() {
    model.setCellState(1, 1);
    assertTrue("Cell should turn into alive state", model.getGrid()[1][1]);
    model.restart();
  }

  /**
   * Test retrieving the grid state to ensure it accurately represents the current game state.
   */
  @Test
  public void testGetGrid() {
    model.setSize(3);
    model.setCellState(0, 0);
    boolean[][] grid = model.getGrid();
    assertTrue("Grid should reflect changes", grid[0][0]);
    assertFalse("Unchanged cells should remain dead", grid[1][1]);
    model.restart();
  }

  /**
   * Verify the model correctly resets its state upon restart.
   */
  @Test
  public void testRestart() {
    model.setCellState(1, 1);
    model.restart();
    assertTrue("Grid should be reset to empty", model.checkInitGridIsEmpty());
    assertEquals("Status should be reset to SEED", EStatus.SEED, model.getStatus());
    assertEquals("Generation should be reset to 0", 0, model.getGeneration());
    model.restart();
  }

  /**
   * Simulate the update of generation and checks if the generation count increments correctly.
   */
  @Test
  public void testGetGeneration() throws InterruptedException{
    model.setStatus(EStatus.RUNNING);
    model.setTime(1);
    model.countDown();
    Thread.sleep(1000);

    assertEquals("Generation should be increased by 1", 1, model.getGeneration());
    model.restart();
  }

  /**
   * Test the countdown logic.
   */
  @Test
  public void testCountDown() throws InterruptedException {
    int initialCountDown = model.getCountDown();
//    model.setTime(2);
    model.setStatus(EStatus.RUNNING);
    model.countDown();
    Thread.sleep(1100);

    assertTrue("Countdown should decrement after 1 second", model.getCountDown() < initialCountDown);
    model.restart();
  }

  /**
   * Verify the initial grid state is empty.
   */
  @Test
  public void testCheckInitGridIsEmpty() {
    model.setSize(10);
    assertTrue("Grid should initially be empty", model.checkInitGridIsEmpty());
    model.setCellState(1, 1);
    assertFalse("Grid should not be empty after setting a cell state", model.checkInitGridIsEmpty());
    model.restart();
  }

  /**
   * Test win by setting a stable configuration and checking if the game correctly identifies a win.
   */
  @Test
  public void testWin() throws InterruptedException{
    model.setSize(10);
    model.setTime(1);
    model.setCellState(1,4);
    model.setCellState(2,3);
    model.setCellState(2,5);
    model.setCellState(3,4);
    model.setStatus(EStatus.RUNNING);
    model.countDown();

    Thread.sleep(2000);
    assertEquals("Game should be in Win status", EStatus.WIN, model.getStatus());
    model.restart();
  }

  /**
   * Test lose by ensuring there are no alive cells.
   */
  @Test
  public void testLose() throws InterruptedException {
    model.setSize(4);
    model.setTime(1);
    model.setCellState(1,1);
    model.setCellState(1,2);
    model.setCellState(2,1);
    model.setCellState(2,2);
    model.setStatus(EStatus.RUNNING);
    model.countDown();

    Thread.sleep(2000);
    assertEquals("Game should be in Lose status", EStatus.LOSE, model.getStatus());
    model.restart();
  }

}
