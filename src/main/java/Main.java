class Main {

    public static void main(String[] args) {
        System.out.println("Hello and welcome to tunnel checkers!!");

        GameBoard gameBoard = new GameBoard();
        gameBoard.play();


        System.out.println("The winner is --->");
        System.out.println(gameBoard.checkWin() == 0? "it is a draw!"
                : (gameBoard.checkWin() > 0? "Player1!" : "Player2!"));
    }
}