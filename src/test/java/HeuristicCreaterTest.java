
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

public class HeuristicCreaterTest {

    @Test
    public void shouldReturnTheValueOfAHeuristicGivenAGameBoard() {
        GameBoard gameBoard = new GameBoard();
        assertEquals(HeuristicCreater.HeuristicFunction(gameBoard), 0);
        gameBoard.getPlayer1().move(gameBoard.getPlayer2());
        assertEquals(HeuristicCreater.HeuristicFunction(gameBoard), 0);
    }
}
