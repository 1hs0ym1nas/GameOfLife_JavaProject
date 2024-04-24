package GameOfLife.View;

import GameOfLife.Controller.AbstractController;
import GameOfLife.Controller.Controller;
import GameOfLife.Model.EStatus;
import GameOfLife.utils.IObserver;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class View implements IObserver {

  private final AbstractController controller = Controller.getControllerInstance();
  private static View instance = null;
  private JButton startButton;
  private JButton pauseButton;
  private JButton restartButton;
  private JSpinner sizeSpinner;
  private JSpinner timeSpinner;
  private JLabel currentGeneration;
  private JLabel countDown;
  private JLabel[][] grid;
  private JPanel gridPanel;
  private final JFrame view = new JFrame("This is the home window");
  private int initTime = 5;
  private int initSize = 10;

  private View() {
    controller.attach(this);

    JPanel buttons =  createButtons();
    JPanel inputs = createTexts();

    JPanel nav = new JPanel();
    nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
    nav.add(buttons);
    nav.add(inputs);

    // Add button panel at the top of the window
    view.add(nav, BorderLayout.NORTH);

    controller.setSize(initSize);
    controller.setTime(initTime);

    this.update();

    // Init the frame
    view.setVisible(true);
    view.setSize(9 * 40, 16 * 40);
    view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  public static View getViewInstance() {
    if (instance == null) {
      instance = new View();
    }
    return instance;
  }

  private JPanel createButtons() {
    JPanel buttonPanel = new JPanel();

    // Create some new buttons to navigate to different card
    startButton = new JButton("Start");
    pauseButton = new JButton("Pause");
    restartButton = new JButton("Restart");

    // Add listener to the buttons
    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          if (controller.getStatus() == EStatus.SEED) {
            controller.setTime((int) timeSpinner.getValue());
            controller.setSize((int) sizeSpinner.getValue());
          }
          controller.setStatus(EStatus.RUNNING);
        } catch (Exception error) {
          JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    pauseButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          if (controller.getStatus() == EStatus.RUNNING) {
            controller.setStatus(EStatus.PAUSE);
          }
        } catch (Exception error) {
          //throw new RuntimeException(ex);
        }
      }
    });
    restartButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          controller.restart();
        } catch (Exception error) {
          //
        }
      }
    });

    // Add button in the button panel
    buttonPanel.add(startButton);
    buttonPanel.add(pauseButton);
    buttonPanel.add(restartButton);

    return buttonPanel;
  }

  private JPanel createTexts() {
    JPanel textPanel = new JPanel(new GridLayout(4, 1));

    // Create spinner for size and time
    sizeSpinner = new JSpinner(new SpinnerNumberModel(initSize, 1, 25, 1));
    timeSpinner = new JSpinner(new SpinnerNumberModel(initTime, 1, 60, 1));

    // Add listener for the size spinner
    sizeSpinner.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        controller.setSize((int) sizeSpinner.getValue());
      }
    });

    // Create label for prompts
    currentGeneration = new JLabel("2");
    currentGeneration.setBackground(Color.white);
    currentGeneration.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    currentGeneration.setOpaque(true);

    countDown = new JLabel("10");
    countDown.setBackground(Color.white);
    countDown.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    countDown.setOpaque(true);

    // Add spinner in the input panel
    textPanel.add(createLabelAndText("Size (1 ~ 25):", sizeSpinner));
    textPanel.add(createLabelAndText("Time (1 ~ 60s):", timeSpinner));
    textPanel.add(createLabelAndText("Current generation:", currentGeneration));
    textPanel.add(createLabelAndText("Count down:", countDown));

    return textPanel;
  }

  private JPanel createLabelAndText(String label, Component component) {
    JPanel panel = new JPanel(new GridLayout(1, 2));
    panel.add(new JLabel(label));
    panel.add(component);
    panel.setBorder(new EmptyBorder(10, 5, 10, 5));

    return panel;
  }

  private JPanel createGrid() {
    // Get the boolean grid from the controller
    boolean[][] booleanGrid = controller.getGrid();
    grid = new JLabel[booleanGrid.length][booleanGrid[0].length];

    // Generate the JLabel grid based on the boolean grid
    for (int i = 0; i < booleanGrid.length; i++) {
      for (int j = 0; j < booleanGrid[i].length; j++) {
        this.grid[i][j] = createCell(booleanGrid[i][j], i, j);
      }
    }

    // Add JLabels representing cells to the panel
    JPanel panel = new JPanel(new GridLayout(this.grid[0].length, this.grid.length));

    for (int i = 0; i < this.grid.length; i++) {
      JPanel rowPanel = new JPanel(new GridLayout(1, this.grid.length));
      for (int j = 0; j < this.grid[0].length; j++) {
        rowPanel.add(this.grid[i][j]);
      }
      panel.add(rowPanel);
    }

    return panel;
  }

  private JLabel createCell(boolean isAlive, int x, int y) {
    JLabel cell = new JLabel("");
    cell.setPreferredSize(new Dimension(50, 50));
    cell.setOpaque(true);

    cell.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
    if (isAlive) {
      cell.setBackground(new Color(102, 178, 255));
    } else {
      cell.setBackground(Color.WHITE);
    }

    // Add listener to the cell to listen to the click event
    cell.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (controller.getStatus() == EStatus.SEED) {
          int size = controller.getSize();
          controller.setCellStatus(x, y);
        }
      }
    });

    return cell;
  }

  public void update() {
    // Disable buttons and spinners based on the game status
    if (controller.getStatus() == EStatus.SEED) {
      // When the game is waiting for user input
      // the stop button should be disabled
      startButton.setEnabled(true);
      pauseButton.setEnabled(false);
      restartButton.setEnabled(true);
      timeSpinner.setEnabled(true);
      sizeSpinner.setEnabled(true);
    } else if (controller.getStatus() == EStatus.RUNNING) {
      // If the game is running
      // the start button should be disabled
      startButton.setEnabled(false);
      pauseButton.setEnabled(true);
      restartButton.setEnabled(false);
      timeSpinner.setEnabled(false);
      sizeSpinner.setEnabled(false);
    } else if (controller.getStatus() == EStatus.PAUSE) {
      // If the game is paused
      // the stop button should be disabled
      startButton.setEnabled(true);
      pauseButton.setEnabled(false);
      restartButton.setEnabled(true);
      timeSpinner.setEnabled(false);
      sizeSpinner.setEnabled(false);
    } else if (controller.getStatus() == EStatus.OVER) {
      // When the game is over
      // user should restart the game
      startButton.setEnabled(false);
      pauseButton.setEnabled(false);
      restartButton.setEnabled(true);
      timeSpinner.setEnabled(false);
      sizeSpinner.setEnabled(false);
    }

    // Update the current generation and count down
    currentGeneration.setText(String.valueOf(controller.getGeneration()));
    countDown.setText(String.valueOf(controller.getCountDown()));

    if (controller.getSize() > 0) {
      if (gridPanel != null) {
        view.remove(gridPanel);
      }
      gridPanel = createGrid();
      view.add(gridPanel, BorderLayout.CENTER);
    }
    view.revalidate();
  }
}