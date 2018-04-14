import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* Abstract class for both the players */
public abstract class APlayer {

    private static final int NUMBER_OF_COLUMNS = 8;
    public abstract Set<Piece> getCheckers();

    public abstract GameBoard move(APlayer opponent);
    protected abstract List<int[]> getAdjacent(int x, int y);
    protected abstract boolean isValidMove(APlayer opponent, Piece piece, int x, int y);
    public abstract List<GameBoard> getValidJumps(APlayer opponent);
    protected abstract boolean isValidJump(APlayer opponent, Piece piece, int x, int y);
    protected abstract GameBoard makeMove(APlayer opponent, Piece piece, int x, int y);
    protected abstract GameBoard jump(APlayer opponent, Piece piece, int x, int y);

    Set<Piece> getPlayerPieces(int playerStartingRow) {
        Set<Piece> players = new HashSet<>();
        for (int i = playerStartingRow; i < playerStartingRow+3; i++) {
            int j = i % 2 == 0 ? 1 : 0;
            while (j < NUMBER_OF_COLUMNS) {
                Piece piece = new Piece(i, j, false);
                players.add(piece);
                j += 2;
            }
        }
        return players;
    }

    int minimax(APlayer opponent, int depth, int alpha, int beta, int currentTurn) {
        if (depth == 0) {
            return HeuristicCreater.HeuristicFunction(new GameBoard(this, opponent, currentTurn));
        }
        List<GameBoard> gameList;
        int initial;
        GameBoard tempBoard;
        if (currentTurn == 0) {
            gameList = getAllValidMoves(opponent);
            initial = Integer.MIN_VALUE;
            for (GameBoard aGameList : gameList) {
                tempBoard = aGameList;

                int result = tempBoard.getPlayer2().minimax(tempBoard.getPlayer1(), depth - 1, alpha, beta, tempBoard.getCurrentTurn());

                initial = Math.max(result, initial);
                alpha = Math.max(alpha, initial);

                if (alpha >= beta)
                    break;
            }
        } else {
            initial = Integer.MAX_VALUE;
            gameList = opponent.getAllValidMoves(this);
            for (GameBoard aGameList : gameList) {
                tempBoard = aGameList;

                int result = tempBoard.getPlayer1().minimax(tempBoard.getPlayer2(), depth - 1, alpha, beta, tempBoard.getCurrentTurn());

                initial = Math.min(result, initial);
                alpha = Math.min(alpha, initial);

                if (alpha >= beta)
                    break;
            }
        }
        return initial;
    }

    List<GameBoard> getAllValidMoves(APlayer opponent) {
        List<GameBoard> res = new ArrayList<>();
        for (Piece checker : getCheckers()) {
            int row = checker.getRow();
            int column = checker.getColumn();
            List<int[]> adjacent = getAdjacent(row, column);
            for (int[] array : adjacent) {
                int x = array[0];
                int y = array[1];
                if (isValidMove(opponent, checker, x, y)) {
                    GameBoard newBoard = makeMove(opponent, checker, x, y);
                    res.add(newBoard);
                }
            }
        }
        res.addAll(getValidJumps(opponent));
        return res;
    }

    public int getColumn(int columnNum){
        if(columnNum<0)
            return 8+columnNum;
        else if(columnNum>7)
            return columnNum%8;
        else
            return columnNum;
    }

    public Set<Piece> getKings(Set<Piece> checkers) {
        Set<Piece> kings = new HashSet<>();
        for (Piece piece : checkers) {
            if (piece.isKing()) {
                kings.add(piece);
            }
        }
        return kings;
    }
}
