import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Player1 implements Player {
    static final int PLAYER_STARTING_ROW = 0;
    static final int NUMBER_OF_COLUMNS = 8;

    private Set<Piece> checkers;
    private Set<Piece> kings;

    Player1() {
        this.checkers.addAll(getPlayerPieces());
        this.kings = new HashSet<>();
    }

    private Set<Piece> getPlayerPieces() {
        Set<Piece> players = new HashSet<>();
        for (int i = PLAYER_STARTING_ROW; i < PLAYER_STARTING_ROW + 3; i++) {
            int j = i % 2 == 0 ? 1 : 0;
            while (j < NUMBER_OF_COLUMNS) {
                Piece piece = new Piece(i, j, false);
                players.add(piece);
                j += 2;
            }
        }
        return players;
    }

    public Set<Piece> getCheckers() {
        return checkers;
    }

    @Override
    public Set<Piece> getKings() {
        return kings;
    }

    public String getPlayerName() {
        return "Player1";
    }

    public boolean move(Set<Piece> oppsitionCheckers) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the row number of piece you want to move");
        int row = s.nextInt();
        System.out.println("Enter the column number of piece you want to move");
        int column = s.nextInt();

        System.out.println("Enter the new row number of piece you want to move");
        int newRow = s.nextInt();

        System.out.println("Enter the new column number of piece you want to move");
        int newColumn = s.nextInt();

        if(isValidMove(row,column,newRow,newColumn,oppsitionCheckers)){
            Iterator<Piece> iterator = checkers.iterator();
            while(iterator.hasNext()){
                Piece peice = iterator.next();
                if(peice.getRow()==row && peice.getColumn()==column){
                    checkers.remove(peice);
                    checkers.add(new Piece(newRow,newColumn,peice.isKing()));
                    break;
                }
            }
        }
        return true;
    }

    private boolean isValidMove(int row, int column, int newRow, int newColumn,Set<Piece> oppsitionCheckers) {

        return
                // there is no pawn diagonally opposite and the checker can be moved
                newRow==row+1 && (newColumn==column+1 || newColumn==column-1) && noPlayerinCell(newRow,newColumn) && noOppositionInCell(newRow,newColumn,oppsitionCheckers);
            
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
