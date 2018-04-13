import java.util.*;

/* This is the human player */
public class Player2 extends APlayer {
    static final int PLAYER_STARTING_ROW = 5;
    private Set<Piece> checkers;

    Player2() {
        this.checkers = new HashSet<>();
        this.checkers.addAll(super.getPlayerPieces(PLAYER_STARTING_ROW));
    }

    public String getPlayerName() {
        return "Player2";
    }

    @Override
    public Set<Piece> getCheckers() {
        return this.checkers;
    }

    @Override
    public Set<Piece> getKings() {
        Set<Piece> kings = new HashSet<>();
        for (Piece piece : checkers) {
            if (piece.isKing()) {
                kings.add(piece);
            }
        }
        return kings;
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

    @Override
    protected List<int[]> getAdjacent(int x, int y) {
        return null;
    }

    @Override
    protected boolean isValidMove(APlayer opponent, Piece piece, int x, int y) {
        return false;
    }

    @Override
    public List<GameBoard> getValidJumps(APlayer opponent) {
        List<GameBoard> gameList = new ArrayList<>();
        for (Piece piece : this.checkers) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (isValidJump(opponent, piece, i, j)) {
                        GameBoard game = jump(opponent, piece, i, j);
                        gameList.add(game);
                    }

                }
            }
        }
        return gameList;
    }

    @Override
    protected boolean isValidJump(APlayer opponent, Piece piece, int x, int y) {
        return false;
    }

    @Override
    protected GameBoard makeMove(APlayer opponent, Piece piece, int x, int y) {
        return null;
    }

    @Override
    protected GameBoard jump(APlayer opponent, Piece piece, int x, int y) {
        return null;
    }

    private boolean isValidMove(int row, int column, int newRow, int newColumn, Set<Piece> oppsitionCheckers) {

        return
                // there is no pawn diagonally opposite and the checker can be moved
                newRow == row - 1 && (newColumn == column + 1 || newColumn == column - 1) && noPlayerInCell(newRow, newColumn) && noOppositionInCell(newRow, newColumn, oppsitionCheckers);

    }

    public boolean noOppositionInCell(int newRow, int newColumn, Set<Piece> oppsitionCheckers) {
        Iterator<Piece> iterator = oppsitionCheckers.iterator();
        while (iterator.hasNext()) {
            Piece peice = iterator.next();
            if (peice.getRow() == newRow && peice.getColumn() == newColumn) {
                return false;
            }
        }
        return true;
    }

    public boolean noPlayerInCell(int newRow, int newColumn) {
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