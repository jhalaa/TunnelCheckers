import java.util.*;

/* Abstract class for both the players */
public abstract class APlayer {

    private static final int NUMBER_OF_COLUMNS = 8;
    public abstract Set<Piece> getCheckers();
    public abstract GameBoard move(APlayer opponent);
    protected abstract boolean isValidMove(APlayer opponent, Piece piece, int x, int y);
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
        Set<Piece> temp = new HashSet<>();
        temp.addAll(getCheckers());
        for (Piece checker : temp) {
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

    public List<GameBoard> getValidJumps(APlayer opponent) {
        List<GameBoard> gameList = new ArrayList<>();
        Set<Piece> temp = new HashSet<>();
        temp.addAll(this.getCheckers());
        for (Piece piece : temp) {
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

    protected List<int[]> getAdjacent(int x, int y) {
        List<int[]> res = new ArrayList<>();
        int[] downleft = new int[2];
        int[] downright = new int[2];
        int[] upleft = new int[2];
        int[] upright = new int[2];
        if (x == 0) {
            getDown(x, res, downleft, y - 1);
            getDown(x, res, downright, y + 1);
            return res;
        }
        if (x == 7) {
            getUp(x, res, upleft, y - 1);
            getUp(x, res, upright, y + 1);
            return res;
        }
        getDown(x, res, downleft, y - 1);
        getDown(x, res, downright, y + 1);
        getUp(x, res, upleft, y - 1);
        getUp(x, res, upright, y + 1);
        return res;
    }

    public void getDown(int x, List<int[]> res, int[] down, int columnNum){
        down[0] = x + 1;
        down[1] = getColumn(columnNum);
        res.add(down);
    }

    public void getUp(int x, List<int[]> res, int[] up, int columnNum) {
        up[0] = x - 1;
        up[1] = getColumn(columnNum);
        res.add(up);
    }

    public boolean hasPlayers(APlayer opponent, int x, int y) {
        Iterator<Piece> iterator = opponent.getCheckers().iterator();
        while(iterator.hasNext()){
            Piece piece = iterator.next();
            if(piece.getRow()==x && piece.getColumn()==y)
                return true;
        }
        return false;
    }

    public boolean isEmpty(APlayer opponent,int x, int y) {
        return !hasPlayers(opponent,x,y) && !hasPlayers(this,x,y);
    }

}
