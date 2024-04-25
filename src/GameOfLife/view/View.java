package GameOfLife.view;

import GameOfLife.controller.AbstractController;
import GameOfLife.utils.EStatus;
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

/**
 * View class for the game of life
 */
public class View implements IObserver {
  private final AbstractController controller;
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
  private final JFrame view = new JFrame("Game of life");
  private final int initTime = 3;
  private final int initSize = 10;

  /**
   * Constructor for the view
   * @param controller the controller for the game
   */
  protected View(AbstractController controller) {
    // Set the controller
    this.controller = controller;
    // Attach the view as observer to the controller
    controller.attach(this);

    // Create buttons and text input boxes to receive user input
    JPanel buttons =  createButtons();
    JPanel inputs = createTexts();

    // Create a nav panel to hold the buttons and text input boxes
    JPanel nav = new JPanel();
    nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
    nav.add(buttons);
    nav.add(inputs);

    // Add button panel at the top of the window
    view.add(nav, BorderLayout.NORTH);

    // Initialize the size of grid and period of time to compute the next generation
    controller.setSize(initSize);
    controller.setTime(initTime);

    this.update();

    // Init the frame
    view.setVisible(true);
    view.setSize(9 * 40, 15 * 40);
    view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  /**
   * A static method to Get the instance of the view
   * @param controller the controller for the game
   * @return the instance of the view
   */
  public static View getViewInstance(AbstractController controller) {
    if (instance == null) {
      instance = new View(controller);
    }
    return instance;
  }

  /**
   * Create buttons for the view
   * @return the JPanel saved the buttons
   */
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
        if (controller.checkInitGridIsEmpty()) {
          // If the grid is empty, show a message dialog
          JOptionPane.showMessageDialog(null, "Please click and initialize the grid before "
              + "starting the game!");
          return;
        }
        // Set the status of the game to running when the start button is pressed
        controller.setStatus(EStatus.RUNNING);
      }
    });

    // Add listener to the pause button to pause the game
    pauseButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.setStatus(EStatus.PAUSE);
      }
    });

    // Add listener to the restart button to restart the game
    restartButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.restart();
      }
    });

    // Add button in the button panel
    buttonPanel.add(startButton);
    buttonPanel.add(pauseButton);
    buttonPanel.add(restartButton);

    return buttonPanel;
  }

  /**
   * Create text input boxes for the view
   * @return the JPanel saved the text input boxes
   */
  private JPanel createTexts() {
    JPanel textPanel = new JPanel(new GridLayout(4, 1));

    // Create spinner for size and time
    sizeSpinner = new JSpinner(new SpinnerNumberModel(initSize, 1, 25, 1));
    timeSpinner = new JSpinner(new SpinnerNumberModel(initTime, 1, 60, 1));

    // Add listener for the size spinner when the text changes
    sizeSpinner.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        // Update the latest size of the grid
        controller.setSize((int) sizeSpinner.getValue());
      }
    });

    // Add listener for the time spinner when the text changes
    timeSpinner.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        // Update the latest time to compute the next generation
        controller.setTime((int) timeSpinner.getValue());
      }
    });

    // Create label for prompts
    currentGeneration = new JLabel("2");
    currentGeneration.setBackground(Color.white);
    currentGeneration.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    currentGeneration.setOpaque(true);
    currentGeneration.setHorizontalAlignment(JLabel.RIGHT);

    countDown = new JLabel("10");
    countDown.setBackground(Color.white);
    countDown.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    countDown.setOpaque(true);
    countDown.setHorizontalAlignment(JLabel.RIGHT);

    // Add spinner in the input panel
    textPanel.add(createLabelAndText("Size (1 ~ 25):", sizeSpinner));
    textPanel.add(createLabelAndText("Time (1 ~ 60s):", timeSpinner));
    textPanel.add(createLabelAndText("Current generation:", currentGeneration));
    textPanel.add(createLabelAndText("Count down:", countDown));

    return textPanel;
  }

  /**
   * Create a label and a component in a panel
   * @param label the label for the component
   * @param component the component to be added
   * @return the panel with the label and the component
   */
  private JPanel createLabelAndText(String label, Component component) {
    JPanel panel = new JPanel(new GridLayout(1, 2));
    panel.add(new JLabel(label));
    panel.add(component);
    panel.setBorder(new EmptyBorder(10, 5, 10, 5));

    return panel;
  }

  /**
   * Create a grid based on the boolean grid
   * @return the JPanel saved the grid
   */
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

  /**
   * Create a cell based on the status of the cell
   * @param isAlive the status of the cell
   * @param x the x coordinate of the cell
   * @param y the y coordinate of the cell
   * @return the JLabel representing the cell
   */
  private JLabel createCell(boolean isAlive, int x, int y) {
    JLabel cell = new JLabel("");

    // Set the style of the cell
    cell.setPreferredSize(new Dimension(50, 50));
    // The opaque property should be set to true to make the background color visible
    cell.setOpaque(true);
    cell.setBorder(BorderFactory.createLineBorder(Color.gray, 1));

    // Set the background color based on the status of the cell
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
        // When the game isn't running or over
        // the user can click the cell to set the status of the cell
        if (controller.getStatus() == EStatus.SEED) {
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
    } else if (controller.getStatus() == EStatus.WIN || controller.getStatus() == EStatus.LOSE) {
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

    // Remove the grid in the view
    // and add the latest grid with updated status
    if (controller.getSize() > 0) {
      if (gridPanel != null) {
        view.remove(gridPanel);
      }
      gridPanel = createGrid();
      view.add(gridPanel, BorderLayout.CENTER);
    }

    // Show a message dialog when the game is over
    if (controller.getStatus() == EStatus.WIN) {
      JOptionPane.showMessageDialog(null, "You win! Click the restart button to play again!");
    } else if (controller.getStatus() == EStatus.LOSE) {
      JOptionPane.showMessageDialog(null, "You lose! Click the restart button to play again!");
    }

    view.revalidate();
  }
}