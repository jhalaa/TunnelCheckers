import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GameBoardTest {

    GameBoard gameBoard = new GameBoard();

    @Test
    public void shouldHaveInitialGameBoardWith12CheckersEach() {
        assertNotEquals(gameBoard.getPlayer2(),null);
        assertNotEquals(gameBoard.getPlayer1(),null);

        assertEquals(gameBoard.getPlayer1().getCheckers().size(),12);
        assertEquals(gameBoard.getPlayer2().getCheckers().size(),12);
    }

    @Test
    public void shouldHaveNoInitialWinner() {
        assertEquals(gameBoard.checkWin(),0);
    }

    @Test
    public void shouldReturnGameOverWhenGameISWon() {
        assertEquals(gameBoard.gameOver(),false);
    }

}
