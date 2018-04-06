import org.junit.Assert;
import org.junit.Test;

public class Player2Test {

    Player player = new Player2();

    @Test
    public void shouldReturnCorrectName(){
        Assert.assertEquals(player.getPlayerName(),"Player2");
    }
}
