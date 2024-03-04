import org.example.GameBoard;
import org.example.Minesweeper_Game;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MinesweeperGameTest {

    @Test
    public void testGameWon() {
        // Create an instance of Minesweeper_Game
        Minesweeper_Game game = new Minesweeper_Game(10, 10);

        // Call the gameWon() method
        game.gameWon();

        // Assert that the gameBoard reveals all non-mine cells
        // You need to add a method or a flag in GameBoard class to check if revealAllNonMineCells() is called
        // assertTrue(game.gameBoard.isAllNonMineCellsRevealed());
    }

    @Test
    public void testResetGame() {
        // Create an instance of Minesweeper_Game
        Minesweeper_Game game = new Minesweeper_Game(10, 10);

        // Call the resetGame() method
        game.resetGame();

        // Assert that the game is reset
        // You need to add a method or a flag in Minesweeper_Game class to check if resetGame() is called
         assertTrue(game.resetGame());
    }

    @Test
    public void testGameOver() {
        // Create an instance of Minesweeper_Game
        Minesweeper_Game game = new Minesweeper_Game(10, 10);

        // Call the gameOver() method
        game.gameOver();

        // Assert that the game is over
        // You need to add a method or a flag in Minesweeper_Game class to check if gameOver() is called
        // assertTrue(game.isGameOver());
    }

}
