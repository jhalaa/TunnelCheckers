import org.junit.Assert;
import org.junit.Test;

public class Player1Test {

    Player player = new Player1();

    @Test
    public void shouldReturnCorrectName(){
        Assert.assertEquals(player.getPlayerName(),"Player1");
    }
}
