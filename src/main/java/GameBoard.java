public class GameBoard {
    private APlayer player1;
    private APlayer player2;
    //private int numberOfTurns;
    private int currentTurn; //0 or 1 to refer to PLAYER1 or PLAYER2

    public GameBoard(){
        player1 = new Player1();
        player2 = new Player2();
        //numberOfTurns = 0;
        currentTurn = 0;
    }

    public GameBoard(APlayer player1, APlayer player2, int currentTurn) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentTurn = currentTurn;
    }

    public APlayer getPlayer1() {
        return player1;
    }

    public APlayer getPlayer2() {
        return player2;
    }

//    public int getNumberOfTurns() {
//        return numberOfTurns;
//    }


    public int getCurrentTurn() {
        return currentTurn;
    }


    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    /**
     *
     * @return the int bigger than 0 means player1 wins, smaller than 0 means player2 wins,
     * and 0 means a draw.
     */
    public int checkWin(){
        if (player1.move(this.getPlayer2()) == null) {
            return -1;
        }
        if (player2.move(this.getPlayer1()) == null) {
            return 1;
        }
        if (player1.getCheckers().size() == player2.getCheckers().size()) {
            return player1.getKings(player1.getCheckers()).size() - player2.getKings(player2.getCheckers()).size();
        }
        return player1.getCheckers().size() - player2.getCheckers().size();
    }

    public boolean gameOver() {
        return player1.getCheckers().size() == 0 || player2.getCheckers().size() == 0
                || player1.move(this.getPlayer2()) == null || player2.move(this.getPlayer1()) == null;
    }
}
