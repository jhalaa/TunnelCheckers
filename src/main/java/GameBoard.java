public class GameBoard {
    private APlayer player1;
    private APlayer player2;
    private int currentTurn; //0 or 1 to refer to PLAYER1 or PLAYER2

    public GameBoard(){
        player1 = new Player1();
        player2 = new Player2();
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
        if (player1.getAllValidMoves(this.getPlayer2()).size() == 0) {
            return -1;
        }
        if (player2.getAllValidMoves(this.getPlayer2()).size() == 0) {
            return 1;
        }
        if (player1.getCheckers().size() == player2.getCheckers().size()) {
            return player1.getKings(player1.getCheckers()).size() - player2.getKings(player2.getCheckers()).size();
        }
        return player1.getCheckers().size() - player2.getCheckers().size();
    }

    public boolean gameOver() {
        return player1.getCheckers().size() == 0 || player2.getCheckers().size() == 0;
//                || player1.getAllValidMoves(this.getPlayer2()).size() == 0 || player2.getAllValidMoves(this.getPlayer2()).size() == 0;
    }

    public void printBoard() {
        System.out.println("     0  1  2  3  4  5  6  7 ");
        System.out.println("-----------------------------");
        for (int i = 0; i < 8; i++) {
            System.out.print(i +"  ");
            for (int j = 0; j < 8; j++) {
                if(player1.getCheckers().contains(new Piece(i, j))) {
                    Piece temp = player1.getChecker(i, j);
                    if (temp.isKing()) {
                        System.out.print("|1K");
                    } else {
                        System.out.print("|⬤");
                    }
                } else if(player2.getCheckers().contains(new Piece(i, j))) {
                    Piece temp = player2.getChecker(i, j);
                    if (temp.isKing()) {
                        System.out.print("|2K");
                    } else {
                        System.out.print("|  ⃝");
                    }
                } else {
                    System.out.print("|  ");
                }
            }
            System.out.print("|");
            System.out.println("");
        }
        System.out.println("-----------------------------");
        System.out.println("");
        System.out.println("");
    }
}
