import java.util.Set;

class Main {

    public static GameBoard gameBoard = new GameBoard();

    public static void main(String[] args) {
        System.out.println("Hello and welcome to tunnel checkers!!");


        while (!gameBoard.gameOver()) {
            if (gameBoard.getCurrentTurn() == 0) {
                gameBoard= gameBoard.getPlayer1().move(gameBoard.getPlayer2());
                printMyBoard(gameBoard);
            } else {
                gameBoard= gameBoard.getPlayer2().move(gameBoard.getPlayer1());
                printMyBoard(gameBoard);
            }
        }

        System.out.println("The winner is --->");
        System.out.println(gameBoard.checkWin() == 0 ? "it is a draw!"
                : (gameBoard.checkWin() > 0 ? "Player1!" : "Player2!"));
    }

    private static void printMyBoard(GameBoard gameBoard) {
        Set<Piece> pieces1 = gameBoard.getPlayer1().getCheckers();
        Set<Piece> pieces2 = gameBoard.getPlayer2().getCheckers();
        for(int i =0;i<8;i++){
            for(int j=0;j<8;j++){
                Piece temp = new Piece(i,j);
                if(pieces1.contains(temp))
                    System.out.print("X");
                if(pieces2.contains(temp))
                    System.out.print("O");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
}