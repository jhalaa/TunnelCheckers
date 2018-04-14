class Main {

    public static GameBoard gameBoard = new GameBoard();

    public static void main(String[] args) {
        System.out.println("Hello and welcome to tunnel checkers!!");


        while (!gameBoard.gameOver()) {
            if (gameBoard.getCurrentTurn() == 0) {
                gameBoard= gameBoard.getPlayer1().move(gameBoard.getPlayer2());
            } else {
                gameBoard= gameBoard.getPlayer2().move(gameBoard.getPlayer1());
            }
        }

        System.out.println("The winner is --->");
        System.out.println(gameBoard.checkWin() == 0 ? "it is a draw!"
                : (gameBoard.checkWin() > 0 ? "Player1!" : "Player2!"));
    }
}