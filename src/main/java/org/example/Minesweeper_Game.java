package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class Minesweeper_Game extends JFrame {
    private final GameBoard gameBoard;

    public Minesweeper_Game(int size, int mines) {
        // Frame settings
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(size, size));

        gameBoard = new GameBoard(size, mines);
        gameBoard.initialize(this);

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

    public static void main(String[] args) {
        launchGame();
    }

    private static void launchGame() {
        Object[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(null,
                "Select Difficulty Level", "Minesweeper",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        final int size;
        final int mines;
        switch (choice) {
            case 1:
                size = 15;
                mines = 20;
                break;
            case 2:
                size = 20;
                mines = 30;
                break;
            default:
                size = 10;
                mines = 10;
                break;
        }

        SwingUtilities.invokeLater(() -> {new Minesweeper_Game(size, mines);});
    }

    public boolean resetGame() {
        dispose();
        launchGame();
        return true;
    }

    public boolean gameWon() {
        gameBoard.revealAllNonMineCells();
        JOptionPane.showMessageDialog(this, "You Win!");
        return true;
    }

    public boolean gameOver() {
       // revealAllMines();
       // game.openRickrollLink();
        // Disable all buttons
        //disableAllButtons();

        // Show game over dialog with options to close or try again
        Object[] options = {"Close Game", "Try Again"};
        int choice = JOptionPane.showOptionDialog(null,
                "Game Over", "Minesweeper Lost",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        // Take action based on user's choice
        if (choice == 1) {
            resetGame();
        } else {
            System.exit(0);
        }
        return true;
    }

    void openRickrollLink() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=xvFZjo5PgG0"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}