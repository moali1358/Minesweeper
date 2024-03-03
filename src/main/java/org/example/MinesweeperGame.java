package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MinesweeperGame extends JFrame {
    private final JButton[][] grid;
    private final boolean[][] mineField;
    private final boolean[][] visited;
    private final int size;

    public MinesweeperGame(int size, int mines) {
        this.size = size;

        // Frame settings
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(size, size));

        // Initialize grid, mineField, and visited arrays
        grid = new JButton[size][size];
        mineField = new boolean[size][size];
        visited = new boolean[size][size];

        // Initialize the game
        initializeGame(size, mines);

        // Set frame size and visibility
        setSize(700, 700);
        setVisible(true);

        // Add key listener for 'r' key to simulate game win
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'r') {
                    gameWon();
                }
            }
        });
        setFocusable(true);
    }

    // Main method to launch the game
    public static void main(String[] args) {
        launchGame();
    }

    // Method to launch the game
    private static void launchGame() {
        // Welcome menu to select difficulty level
        Object[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(null,
                "Select Difficulty Level", "Minesweeper",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        final int size;
        final int mines = switch (choice) {
            case 1 -> {
                size = 15;
                yield 20;
            }
            case 2 -> {
                size = 20;
                yield 30;
            }
            default -> {
                size = 10;
                yield 10;
            }
        };

        // Start the game with chosen difficulty level
        SwingUtilities.invokeLater(() -> new MinesweeperGame(size, mines));
    }

    // Method to reset the game
    private void resetGame() {
        // Close the current game window
        dispose();
        // Start a new game
        launchGame();
    }

    // Method to initialize the game
    private void initializeGame(int size, int mines) {
        // Create grid buttons and add them to the frame
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new JButton();
                final int x = i;
                final int y = j;

                // Add mouse listener to handle left and right clicks
                grid[i][j].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            markMine(x, y);
                        } else {
                            reveal(x, y);
                        }
                    }
                });

                // Add button to the frame
                add(grid[i][j]);
            }
        }

        // Place mines randomly
        placeMinesRandomly(mines);
    }

    // Method to place mines randomly
    private void placeMinesRandomly(int mines) {
        for (int i = 0; i < mines; i++) {
            int x = (int) (Math.random() * size);
            int y = (int) (Math.random() * size);
            if (mineField[x][y]) {
                i--;
                continue;
            }
            mineField[x][y] = true;
        }
    }

    // Method to reveal a cell
    private void reveal(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size || visited[x][y]) {
            return;
        }

        visited[x][y] = true;
        if (mineField[x][y]) {
            revealAllMines();
            gameOver();
            return;
        }

        int count = countAdjacentMines(x, y);
        grid[x][y].setText(count > 0 ? Integer.toString(count) : "");
        grid[x][y].setEnabled(false);

        if (count == 0) {
            // If no adjacent mines, recursively reveal surrounding cells
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    reveal(x + i, y + j);
                }
            }
        }
    }

    // Method to mark a mine with a flag
    private void markMine(int x, int y) {
        if (grid[x][y].getText().isEmpty()) {
            grid[x][y].setText("M");
        } else if (grid[x][y].getText().equals("M")) {
            grid[x][y].setText("");
        }
    }

    // Method to count adjacent mines
    private int countAdjacentMines(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = x + i;
                int newY = y + j;
                if (newX >= 0 && newX < size && newY >= 0 && newY < size && mineField[newX][newY]) {
                    count++;
                }
            }
        }
        return count;
    }

    // Method to handle game over
    private void gameOver() {
        // Reveal all mines
        revealAllMines();

        // Disable all buttons
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j].setEnabled(false);
            }
        }

        // Show game over dialog with options to close or try again
        Object[] options = {"Close Game", "Try Again"};
        int choice = JOptionPane.showOptionDialog(null,
                "Game Over", "Minesweeper Lost",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        // Take action based on user's choice
        switch (choice) {
            case 0:
                System.exit(0);
                break;
            case 1:
                resetGame();
                break;
        }
    }

    // Method to simulate winning the game
    private void gameWon() {
        // Reveal all non-mine cells
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!mineField[i][j]) {
                    grid[i][j].setText("");
                    grid[i][j].setEnabled(false);
                }
            }
        }
        JOptionPane.showMessageDialog(this, "You Win!");
    }

    // Method to reveal all mines at the end of the game
    private void revealAllMines() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mineField[i][j]) {
                    grid[i][j].setText("*");
                }
            }
        }
    }
}
