import java.util.*;

public class Player2 extends APlayer {
    static final int PLAYER_STARTING_ROW = 5;
    static final int NUMBER_OF_COLUMNS = 8;
    private Set<Piece> checkers;

    Player2(){
        super(PLAYER_STARTING_ROW);
    }

    public String getPlayerName() {
        return "Player2";
    }

    public GameBoard move(APlayer opponent) {
        Set<Piece> oppositionCheckers = opponent.getCheckers();
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the row number of piece you want to move");
        int row = s.nextInt();
        System.out.println("Enter the column number of piece you want to move");
        int column = s.nextInt();

        System.out.println("Enter the new row number of piece you want to move");
        int newRow = s.nextInt();

        System.out.println("Enter the new column number of piece you want to move");
        int newColumn = s.nextInt();

        if (isValidMove(row, column, newRow, newColumn, oppositionCheckers)) {
            Iterator<Piece> iterator = checkers.iterator();
            while (iterator.hasNext()) {
                Piece peice = iterator.next();
                if (peice.getRow() == row && peice.getColumn() == column) {
                    checkers.remove(peice);
                    checkers.add(new Piece(newRow, newColumn, peice.isKing()));
                    return new GameBoard(this, opponent, 0);
                }
            }
        }
        return null;
    }

    private boolean isValidMove(int row, int column, int newRow, int newColumn, Set<Piece> oppsitionCheckers) {

        return
                // there is no pawn diagonally opposite and the checker can be moved
                newRow == row - 1 && (newColumn == column + 1 || newColumn == column - 1) && noPlayerinCell(newRow, newColumn) && noOppositionInCell(newRow, newColumn, oppsitionCheckers);

    }

    private boolean noOppositionInCell(int newRow, int newColumn, Set<Piece> oppsitionCheckers) {
        Iterator<Piece> iterator = oppsitionCheckers.iterator();
        while (iterator.hasNext()) {
            Piece peice = iterator.next();
            if (peice.getRow() == newRow && peice.getColumn() == newColumn) {
                return false;
            }
        }
        return true;
    }

    private boolean noPlayerinCell(int newRow, int newColumn) {
        Iterator<Piece> iterator = checkers.iterator();
        while (iterator.hasNext()) {
            Piece peice = iterator.next();
            if (peice.getRow() == newRow && peice.getColumn() == newColumn) {
                return false;
            }
        }
        return true;
    }

}