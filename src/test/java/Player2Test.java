

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Player2Test {

    private Player2 player = new Player2();


    @Test
    public void shouldReturnCorrectInitialCheckers(){
         assertEquals(this.player.getCheckers().size(),12);

         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==0).count(),0);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==1).count(),0);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==2).count(),0);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==3).count(),0);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==4).count(),0);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==5).count(),4);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==6).count(),4);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==7).count(),4);

         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==0).count(),2);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==2).count(),2);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==4).count(),2);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==6).count(),2);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==1).count(),1);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==3).count(),1);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==5).count(),1);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==7).count(),1);
    }

    @Test
    public void shouldGetAllTheKingCheckers() {
         assertEquals(this.player.getKings().size(),0);

    }

}
