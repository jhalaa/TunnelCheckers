import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

import java.util.Set;

public class Player1Test {

    Player player = new Player1();

    @Test
    public void shouldReturnCorrectName(){
        assertEquals(player.getPlayerName(),"Player1");
    }

    @Test
    public void shouldReturnCorrectInitialCheckers(){
        assertEquals(this.player.getCheckers().size(),12);

         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==0).count(),4);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==1).count(),4);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==2).count(),4);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==3).count(),0);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==4).count(),0);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==5).count(),0);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==6).count(),0);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getRow()==7).count(),0);

         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==0).count(),1);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==2).count(),1);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==4).count(),1);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==6).count(),1);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==1).count(),2);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==3).count(),2);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==5).count(),2);
         assertEquals(player.getCheckers().stream().filter(piece -> piece.getColumn()==7).count(),2);
    }

    @Test
    public void shouldGetAllTheKingCheckers() {
         assertEquals(this.player.getKings().size(),0);

    }

//    @Test
//    public void shouldCheckIfNoneOfItsPlayersAreInACell() {
//         assertEquals(this.player.noPlayerInCell(3,0),true);
//         assertEquals(this.player.noPlayerInCell(4,3),true);
//         assertEquals(this.player.noPlayerInCell(5,1),true);
//         assertEquals(this.player.noPlayerInCell(5,0),false);
//
//    }
//
//    @Test
//    public void shouldCheckIfNoneOfOppositionPlayersAreInACell() {
//        Set<Piece> oppsitionCheckers = new Player1().getCheckers();
//         assertEquals(this.player.noOppositionInCell(3,0, oppsitionCheckers),true);
//         assertEquals(this.player.noOppositionInCell(4,3, oppsitionCheckers),true);
//         assertEquals(this.player.noOppositionInCell(0,0, oppsitionCheckers),true);
//         assertEquals(this.player.noOppositionInCell(0,1, oppsitionCheckers),false);
//
//    }
}
