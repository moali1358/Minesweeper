import org.example.GameBoard;
import org.example.Minesweeper_Game;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.awt.event.KeyEvent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MinesweeperGameTest {
    @Test
    public void testGameWonIsCalled() {
        Minesweeper_Game game = new Minesweeper_Game(10, 10);
        KeyEvent mockedKeyEvent = new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_R, 'r');
        game.dispatchEvent(mockedKeyEvent);
        assertTrue(game.gameWon());
    }
    @Test
    public void testGameWon() {
        Minesweeper_Game game = new Minesweeper_Game(10, 10);
        game.gameWon();
        assertTrue(game.gameWon());
    }
    @Test
    public void testResetGame() {
        Minesweeper_Game game = new Minesweeper_Game(10, 10);
        game.resetGame();
        assertTrue(game.resetGame());
    }
    @Test
    public void testGameOver() {
        Minesweeper_Game game = new Minesweeper_Game(10, 10);
        game.gameOver();
        assertTrue(game.gameOver());
    }

}
