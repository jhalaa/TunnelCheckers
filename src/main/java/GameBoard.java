import java.util.HashSet;
import java.util.Set;

public class GameBoard {
    private Player player1;
    private Player player2;
    private int numberOfTurns = 0;



    public GameBoard(){
        player1 = new Player1();
        player2 = new Player2();
        numberOfTurns = 0;
    }


//    void deletePiece(Piece piece){
//        this.players.remove(piece);
//    }
//
//    public PLAYER getWinner(){
//        return this.winner;
//    }
//
//    public boolean checkWin(){
//        if(players.stream().allMatch(player -> player.getOwner().equals(PLAYER.ONE)))
//            winner=PLAYER.ONE;
//        if(players.stream().allMatch(player -> player.getOwner().equals(PLAYER.TWO)))
//            winner=PLAYER.TWO;
//        return this.winner!=null;
//    }

    public void play() {
//        while(!this.gameOver()){
//            if(this.numberOfTurns++ %2==0)
//                player1.move();
//            else
//                player2.move();
//        }
    }

}
