package org.example;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameBoard {
    private final JButton[][] grid;
    private final boolean[][] mineField;
    private final boolean[][] visited;
    private final int size;

    public GameBoard(int size, int mines) {
        // Initialize grid, mineField, and visited arrays
        this.size = size;
        grid = new JButton[size][size];
        mineField = new boolean[size][size];
        visited = new boolean[size][size];
        placeMinesRandomly(mines);
    }

    public void initialize(Minesweeper_Game game) {
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
                            reveal(x, y, game);
                        }
                    }
                });

                game.add(grid[i][j]);
            }
        }
    }

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

    private void reveal(int x, int y, Minesweeper_Game game) {
        if (x < 0 || x >= size || y < 0 || y >= size || visited[x][y]) {
            return;
        }

        visited[x][y] = true;
        if (mineField[x][y]) {
            revealAllMines(game);
            game.openRickrollLink();
            game.gameOver();
            return;
        }

        int count = countAdjacentMines(x, y);
        grid[x][y].setText(count > 0 ? Integer.toString(count) : "");
        grid[x][y].setEnabled(false);

        if (count == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    reveal(x + i, y + j, game);
                }
            }
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

    private void revealAllMines(Minesweeper_Game game) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mineField[i][j]) {
                    grid[i][j].setText("O");
                }
            }
        }
        disableAllButtons();
    }

    private void disableAllButtons() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j].setEnabled(false);
            }
        }
    }

    public void revealAllNonMineCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!mineField[i][j]) {
                    grid[i][j].setText("");
                    grid[i][j].setEnabled(false);
                }
            }
        }
        disableAllButtons();
    }
}