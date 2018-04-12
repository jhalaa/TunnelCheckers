import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player1 implements Player {
    static final int PLAYER_STARTING_ROW = 0;
    static final int NUMBER_OF_COLUMNS = 8;

    private Set<Piece> checkers;
    private Set<Piece> kings;

    Player1(){
        this.checkers.addAll(getPlayerPieces());
        this.kings = new HashSet<>();
    }

    private Set<Piece> getPlayerPieces() {
        Set<Piece> players = new HashSet<>();
        for (int i = PLAYER_STARTING_ROW; i < PLAYER_STARTING_ROW+3; i++) {
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

    public boolean move() {
        return false;
    }

    public List<GameBoard> getAllValidMoves(Player opponent) {
        List<GameBoard> res = new ArrayList<>();
        for (Piece checker : getCheckers()) {

        }

        res.addAll(getValidJumps(opponent));
        return res;
    }

    private int[][] getAdjacent(int x, int y) {
        int[][] res = new int[4][2];


        return res;
    }

    public GameBoard makeJump(Player opponent, Piece piece, int x, int y) {

    }

    public List<GameBoard> getValidJumps(Player opponent) {

    }

    public GameBoard makeMove(Player opponent, Piece piece, int x, int y) {
        if (!isValidMove(opponent, piece, x, y)) {
            return new GameBoard(this, opponent, 0);
        }
        Piece newPiece = null;
        if (getKings().contains(piece) || x == NUMBER_OF_COLUMNS - 1) {
            newPiece = new Piece(x, y, true);
            getKings().remove(piece);
            getKings().add(newPiece);
        }
        newPiece = new Piece(x, y, false);
        getCheckers().remove(piece);
        getCheckers().add(newPiece);
        return new GameBoard(this, opponent, 1);
    }

    public boolean isValidMove(Player opponent, Piece piece, int x, int y) {
        Set<Piece> opponentCheckers = opponent.getCheckers();
        Piece temp = null;
        if (x == 0) {
            temp = new Piece(x, y, true);
        } else {
            temp = new Piece(x, y, false);
        }
        if (opponentCheckers.contains(temp) || !getCheckers().contains(piece)) {
            return false;
        }
        int preX = piece.getRow();
        int preY = piece.getColumn();
        if (!getKings().contains(piece)) {
            if (preX != 0 && x == preX - 1) {
                if (preY == 0) {
                    return y == NUMBER_OF_COLUMNS - 1 || y == preY + 1;
                }
                return y == preY - 1 || y == preY + 1;
            }
        } else {
            if (x == preX + 1) {
                if (preY == 0) {
                    return y == NUMBER_OF_COLUMNS - 1 || y == preY + 1;
                }
                return y == preY - 1 || y == preY + 1;
            }
        }
        return false;
    }


    public boolean isValidJump(Player opponent, Piece piece, int x, int y) {


        return false;
    }
}
