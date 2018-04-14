import java.util.Set;

class Main {



    public static void main(String[] args) {
        System.out.println("Hello and welcome to tunnel checkers!!");
        GameBoard gameBoard = new GameBoard();
        gameBoard.printBoard();

        while (!gameBoard.gameOver()) {
            if (gameBoard.getCurrentTurn() == 0) {
                System.out.println("AI is playing...");
                gameBoard= gameBoard.getPlayer1().move(gameBoard.getPlayer2());
                gameBoard.printBoard();
            } else {
                gameBoard= gameBoard.getPlayer2().move(gameBoard.getPlayer1());
                gameBoard.printBoard();
            }
        }


        System.out.println("The winner is --->");
        System.out.println(gameBoard.checkWin() == 0 ? "it is a draw!"
                : (gameBoard.checkWin() > 0 ? "Player1!" : "Player2!"));
    }
}