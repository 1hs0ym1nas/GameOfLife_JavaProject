package GameOfLife.View.components;

import javax.swing.JButton;

public class Buttons {
  private final JButton startButton;
  private final JButton pauseButton;
  private final JButton restartButton;

  public Buttons() {
    startButton = new JButton("Start");
    pauseButton = new JButton("Pause");
    restartButton = new JButton("Restart");
  }

  public JButton getStartButton() {
    return startButton;
  }

  public JButton getStopButton() {
    return pauseButton;
  }

  public JButton getRestartButton() {
    return restartButton;
  }
}
