# Game of Life


## About/Overview
The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970. 
This project is a Java GUI application of this game, which simulates the birth and death of cells based on a set of rules.


## List of Features

- **Flexible Grid Size**: Users can determine the size of the game grid
- **Adjustable Time**: Control the simulation speed by setting the timer for generations
- **Interactive**: Toggle cells between alive and dead by clicking on the cells
- **Automatic Evolution**: The game processes and conducts automatically after starting


## Getting Started

To run the Primate Sanctuary program: Run the JAR file
Ensure you have Java Runtime Environment (JRE) installed on your machine and navigate to the directory containing the GameOfLife_5004Project.jar file (in res/ folder)
Run the command: java -jar GameOfLife_5004Project.jar


## Prerequisites

Things you need to install the software:
- Java Development Kit (JDK) - Java 11 or higher
- A Java IDE (IntelliJ IDEA, Eclipse, etc.) or a simple text editor


## How to Use the Program

Once launching the application, you will be able to see the interactive window.

Start the application and use the control buttons:
- Set Size: Input the grid size and set it.
- Toggle Cells: Click on cells to toggle them between alive and dead(click once more after it is alive if you want to reset the cell to dead).
- Start/Stop: Start or pause the simulation.
- Reset: Reset the grid to its initial state.


## Design/Model Changes

Version 1.0: Initial release with basic functions.
Version 1.1: Updated UI and fixed some errors.
Version 1.2: Added error handling for full isolation units.


## Assumptions

Assuming that:
- The grid is a square, with dimensions N×N. While N can be set by the user, there is a recommended minimum size of 10x10 at initialization.
- The grid has no wrap-around behavior. Cells on the edges have fewer neighbors than those in the middle, potentially affecting their survival and regeneration.
- The game follows the traditional rules of Conway’s Game of Life:
  - A live cell with two or three live neighbors survives to the next generation
  - A live cell with more than three live neighbors dies (overcrowding)
  - A live cell with fewer than two live neighbors dies (loneliness)
  - An empty cell with exactly three live neighbors becomes a live cell (birth)
- User can pause, resume, and reset the simulation at any time. User can alter the state of the grid when the simulation is paused.

## Contacts

Zhimin Gan, Minghui Xu

## Acknowledgments

John Conway, for the original idea of the Game of Life
