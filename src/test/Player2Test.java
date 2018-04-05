import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Player2Test {

    Player player = new Player2();

    @Test
    public void shouldReturnCorrectName(){
        assertEquals(player.getPlayerName(),"Player2");
    }
}
