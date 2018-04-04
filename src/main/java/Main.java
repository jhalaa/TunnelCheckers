class Main {

    public static void main(String[] args) {
        System.out.println("Hello and welcome to tunnel checkers!!");

        GameBoard gameBoard = new GameBoard();
        while(!gameBoard.checkWin()){
            gameBoard.play();
            gameBoard.toggleCurrentPlayer();
        }


        System.out.println("The winner is --->");
        System.out.println(gameBoard.getWinner());
    }
}