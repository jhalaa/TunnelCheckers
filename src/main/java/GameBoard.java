import java.util.HashSet;
import java.util.Set;

public class GameBoard {
    private Player player1;
    private Player player2;
    //private int numberOfTurns;
    private int currentTurn; //0 or 1 to refer to PLAYER1 or PLAYER2

    public GameBoard(){
        player1 = new Player1();
        player2 = new Player2();
        //numberOfTurns = 0;
        currentTurn = 0;
    }

    public GameBoard(Player player1, Player player2, int currentTurn) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentTurn = currentTurn;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

//    public int getNumberOfTurns() {
//        return numberOfTurns;
//    }


    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     *
     * @return the int bigger than 0 means player1 wins, smaller than 0 means player2 wins,
     * and 0 means a draw.
     */
    public int checkWin(){
        if (!player1.move()) {
            return -1;
        }
        if (!player2.move()) {
            return 1;
        }
        if (player1.getCheckers().size() == player2.getCheckers().size()) {
            return player1.getKings().size() - player2.getKings().size();
        }
        return player1.getCheckers().size() - player2.getCheckers().size();
    }

    public boolean gameOver() {
        return player1.getCheckers().size() == 0 || player2.getCheckers().size() == 0
                || !player1.move() || !player2.move();
    }

    public void play() {
        while(!this.gameOver()){
            if(this.currentTurn == 0) {
                player1.move();
                this.currentTurn = 1;
            } else {
                player2.move();
                this.currentTurn = 0;
            }
        }
    }

}
