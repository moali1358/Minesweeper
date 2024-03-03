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
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(size, size));

        grid = new JButton[size][size];
        mineField = new boolean[size][size];
        visited = new boolean[size][size];

        initializeGame(size, mines);

        setSize(700, 700);
        setVisible(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'r') {
                    gameWon();
                }
            }
        });
        setFocusable(true);
    }

    private void initializeGame(int size, int mines) {
        // Initialize grid buttons and add them to the frame
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new JButton();
                final int x = i;
                final int y = j;
                grid[i][j].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            markMine(x, y);
                        } else {
                            reveal(x, y);
                        }
                    }
                });
                add(grid[i][j]);
            }
        }

        // Place mines randomly
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
            reveal(x - 1, y - 1);
            reveal(x - 1, y);
            reveal(x - 1, y + 1);
            reveal(x, y - 1);
            reveal(x, y + 1);
            reveal(x + 1, y - 1);
            reveal(x + 1, y);
            reveal(x + 1, y + 1);
        }
    }

    private void markMine(int x, int y) {
        if (grid[x][y].getText().isEmpty()) {
            grid[x][y].setText("M");
        } else if (grid[x][y].getText().equals("M")) {
            grid[x][y].setText("");
        }
    }

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

    private void gameOver() {
        revealAllMines();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j].setEnabled(false);
            }
        }
        Object[] options = {"Close Game", "Try Again"};
        int choice = JOptionPane.showOptionDialog(null,
                "Game Over", "Minesweeper Lost",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        switch (choice) {
            case 0:
                System.exit(0);
                break;
            case 1:
                resetGame();
                break;
        }

    }

    private void gameWon() {
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

    private void revealAllMines() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mineField[i][j]) {
                    grid[i][j].setText("*");
                }
            }
        }
    }

    public static void main(String[] args) {
        launchGame();
    }

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
                yield 40;
            }
            default -> {
                size = 10;
                yield 10;
            }
        };

        // Start the game with chosen difficulty level
        SwingUtilities.invokeLater(() -> new MinesweeperGame(size, mines));
    }

    private void resetGame() {
        dispose(); // Close the current game window
        launchGame(); // Start a new game
    }
}
