package model;

import org.junit.Before;
import org.junit.Test;

import GameOfLife.model.Cell;

import static org.junit.Assert.*;

/**
 * This is a test suite for the Cell class.
 */
public class CellTest {
  private Cell cell;
  private boolean[][] grid;

  @Before
  public void setUp() {
    cell = new Cell();
    grid = new boolean[4][4];
  }

  /**
   * Test the initial state of a cell is dead.
   */
  @Test
  public void testInitialState() {
    assertFalse(cell.getState());
  }

  /**
   * Test set and get the state of a cell.
   */
  @Test
  public void testSetAndGetState() {
    cell.setState(true);
    assertTrue("Cell state should be alive after setting to true", cell.getState());
  }

  /**
   * Test when a cell having exactly two neighbors.
   */
  @Test
  public void testCellWithTwoNeighbors() {
    grid[1][1] = true;
    grid[0][0] = true;
    grid[2][2] = true;

    cell.setState(true);
    cell.updateState(1, 1, grid);
    assertTrue("Cell should be alive with exactly two neighbors", cell.getState());
  }

  /**
   * Test when a dead cell having three neighbors.
   */
  @Test
  public void testDeadCellWithThreeNeighbors() {
    grid[1][1] = false;
    grid[0][0] = true;
    grid[0][1] = true;
    grid[0][2] = true;

    cell.setState(false);
    cell.updateState(1, 1, grid);
    assertTrue("Cell should be alive with exactly three neighbors", cell.getState());
  }

  /**
   * Test when a cell dying due to underpopulation.
   */
  @Test
  public void testCellWithOneNeighbor() {
    grid[1][1] = true;
    grid[0][0] = true;

    cell.setState(true);
    cell.updateState(1, 1, grid);
    assertFalse("Cell should die with one neighbor due to underpopulation", cell.getState());
  }

  /**
   * Test when a cell dying due to overpopulation.
   */
  @Test
  public void testCellWithThreeOrMoreNeighbors() {
    grid[1][1] = true;
    grid[0][1] = true;
    grid[1][0] = true;
    grid[1][2] = true;

    cell.setState(true);
    cell.updateState(1, 1, grid);
    assertFalse("Cell should die with three neighbors due to overpopulation", cell.getState());
  }


}
