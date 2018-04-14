import org.junit.Assert;
import org.junit.Test;

public class HeuristicCreaterTest {

    @Test
    public void shouldReturnTheValueOfAHeuristicGivenAGameBoard() {
        GameBoard gameBoard = new GameBoard();
        Assert.assertEquals(HeuristicCreater.HeuristicFunction(gameBoard), 0);

    }
}
