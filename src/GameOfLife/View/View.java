package GameOfLife.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class View {
  private JFrame view;
  private static View instance = null;

  private View() {
    // Create a frame
    view = new JFrame("This is the home window");

    JPanel buttons =  createButtons();
    JPanel inputs = createTexts();

    JPanel nav = new JPanel();
    nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
    nav.add(buttons);
    nav.add(inputs);

    // Add button panel at the top of the window
    view.add(nav, BorderLayout.NORTH);

    view.add(createGrid(), BorderLayout.CENTER);

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
    JButton startButton = new JButton("Start");
    JButton stopButton = new JButton("Stop");
    JButton restartButton = new JButton("Restart");

    // Add button in the button panel
    buttonPanel.add(startButton);
    buttonPanel.add(stopButton);
    buttonPanel.add(restartButton);

    return buttonPanel;
  }

  private JPanel createTexts() {
    JPanel textPanel = new JPanel(new GridLayout(4, 1));

    // Create spinner for size and time
    JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
    JSpinner timeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 60, 1));

    // Create label for prompts
    JLabel currentGeneration = new JLabel("2");
    currentGeneration.setBackground(Color.white);
    currentGeneration.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    currentGeneration.setOpaque(true);

    JLabel countDown = new JLabel("10");
    countDown.setBackground(Color.white);
    countDown.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    countDown.setOpaque(true);

    // Add spinner in the input panel
    textPanel.add(createLabelAndText("Size:", sizeSpinner));
    textPanel.add(createLabelAndText("Time:", timeSpinner));
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
    boolean[][] grid = { {false, true, false}, {true, false, false}, {false, true, true}};
    JPanel panel = new JPanel(new GridLayout(grid[0].length, grid.length));

    for (boolean[] row: grid) {
      JPanel rowPanel = new JPanel(new GridLayout(1, row.length));
      for (boolean isAlive: row) {
        rowPanel.add(createCell(isAlive));
      }
      panel.add(rowPanel);
    }


    return panel;
  }

  private JLabel createCell(boolean isAlive) {
    JLabel cell = new JLabel("");
    cell.setPreferredSize(new Dimension(50, 50));
    cell.setOpaque(true);

    cell.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
    if (isAlive) {
      cell.setBackground(new Color(102, 178, 255));
    } else {
      cell.setBackground(Color.WHITE);
    }

    return cell;
  }
}