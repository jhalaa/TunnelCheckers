import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Player1Test {

    Player player = new Player1();

    @Test
    public void shouldReturnCorrectName(){
        assertEquals(player.getPlayerName(),"Player1");
    }
}
