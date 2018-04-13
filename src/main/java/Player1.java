import java.util.*;

/* This is the AI player */
public class Player1 extends APlayer {
    static final int PLAYER_STARTING_ROW = 0;
    static final int NUMBER_OF_COLUMNS = 8;

    private Set<Piece> checkers;

    Player1(){
        this.checkers = new HashSet<>();
        this.checkers.addAll(super.getPlayerPieces(PLAYER_STARTING_ROW));
    }

    public String getPlayerName() {
        return "Player1";
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
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        List<GameBoard> gameList = getAllValidMoves(opponent);

        List<Integer> heuristics = new ArrayList<>();
        if(gameList.isEmpty())
            return null;
        GameBoard tempBoard = null;

        //initialize the depth to search
        int depth = 4;

        for(int i = 0; i < gameList.size(); i++) {
            tempBoard = gameList.get(i);

            heuristics.add(tempBoard.getPlayer2().minimax(tempBoard.getPlayer1(), depth - 1, alpha, beta, tempBoard.getCurrentTurn()));
        }

        int maxHeuristics = Integer.MIN_VALUE;

        for(int i = heuristics.size() - 1; i >= 0; i--) {
            if (heuristics.get(i) >= maxHeuristics) {
                maxHeuristics = heuristics.get(i);
            }
        }

        for(int i = 0; i < heuristics.size(); i++) {
            if(heuristics.get(i) < maxHeuristics) {
                heuristics.remove(i);
                gameList.remove(i);
                if (i != 0) {
                    i--;
                }
            }
        }

        Random rand = new Random();
        return gameList.get(rand.nextInt(gameList.size()));
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

    private void getDown(int x, List<int[]> res, int[] down, int columnNum) {
        down[0] = x + 1;
        down[1] = getColumn(columnNum);
        res.add(down);
    }

    private void getUp(int x, List<int[]> res, int[] up, int columnNum) {
        up[0] = x - 1;
        up[1] = getColumn(columnNum);
        res.add(up);
    }


    public GameBoard makeMove(APlayer opponent, Piece piece, int x, int y) {
        Piece newPiece = null;
        if (piece.isKing() || x == NUMBER_OF_COLUMNS - 1) {
            newPiece = new Piece(x, y, true);
        } else {
            newPiece = new Piece(x, y, false);
        }
        getCheckers().remove(piece);
        getCheckers().add(newPiece);
        return new GameBoard(this, opponent, 1);
    }

    protected boolean isValidMove(APlayer opponent, Piece piece, int x, int y) {
        // without jumps

        if (hasPlayers(opponent, x, y) || !getCheckers().contains(piece)) {
            return false;
        }
        int preX = piece.getRow();
        int preY = piece.getColumn();
        if (piece.isKing()) {
            if (preX != 0 && x == preX - 1) {
                return y == getColumn(preY - 1) || y == getColumn(preY + 1);
            }
        }
        if (x == preX + 1) {
            return y == getColumn(preY - 1) || y == getColumn(preY + 1);
        }

        return false;
    }

    @Override
    public List<GameBoard> getValidJumps(APlayer opponent) {
        return null;
    }

    private boolean isEmpty(Player opponent,int x, int y) {
        return !hasPlayers(opponent,x,y) && !hasPlayers(this,x,y);
    }

    private boolean hasPlayers(Player opponent, int x, int y) {
        Iterator<Piece> iterator = opponent.getCheckers().iterator();
        while(iterator.hasNext()){
            Piece piece = iterator.next();
            if(piece.getRow()==x && piece.getColumn()==y)
                return true;
        }
        return false;
    }

    public boolean isValidJump(APlayer opponent, Piece piece, int x, int y) {
        if(x < 0 || x > 7) {
            return false;
        }
        int row = piece.getRow();
        int column = piece.getColumn();
        Piece piece1 = new Piece(row+2,getColumn(column-2),piece.isKing());
        Piece piece2 = new Piece(row+2,getColumn(column+2),piece.isKing());
        if(piece.isKing() && x < row) {
            Piece piece3 = new Piece(row-2, getColumn(column-2), true);
            Piece piece4 = new Piece(row-2, getColumn(column+2), true);
            return x==row-2 && y==column-2 && hasPlayers(opponent, row-1, getColumn(column-1)) &&
                    isEmpty(opponent, row-2, getColumn(column-2)) || isValidJump(opponent, piece3, x, y)
                    || x==row-2 && y==getColumn(column+2) && hasPlayers(opponent, row-1,getColumn(column+1)) &&
                    isEmpty(opponent, row-2, getColumn(column+2)) || isValidJump(opponent, piece4, x, y);
        }
        return x==row+2 && y== column-2 && hasPlayers(opponent, row +1, getColumn(column -1)) &&
                isEmpty(opponent, row +2, getColumn(column -2)) || isValidJump(opponent,piece1,x,y) ||
                x==row+2 && y== getColumn(column+2) && hasPlayers(opponent, row +1, getColumn(column +1)) &&
                        isEmpty(opponent, row +2, getColumn(column +2)) || isValidJump(opponent,piece2,x,y);
    }

    public GameBoard jump(APlayer opponent, Piece piece, int x, int y) {
        int row = piece.getRow();
        int column = piece.getColumn();
        Piece piece1 = new Piece(row+2,getColumn(column-2),piece.isKing());
        Piece piece2 = new Piece(row+2,getColumn(column+2),piece.isKing());
        if(piece.isKing() && x < row) {
            Piece piece3 = new Piece(row-2, getColumn(column-2), true);
            Piece piece4 = new Piece(row-2, getColumn(column+2), true);
            if(x==row-2 && y==column-2 && hasPlayers(opponent, row-1, getColumn(column-1)) &&
                    isEmpty(opponent, row-2, getColumn(column-2))) {
                opponent.getCheckers().remove(new Piece(row-1,getColumn(column-1)));
                this.getCheckers().remove(piece);
                this.getCheckers().add(piece3);
                return jump(opponent, piece3, x, y);
            }
            if (x==row-2 && y==getColumn(column+2) && hasPlayers(opponent, row-1,getColumn(column+1)) &&
                    isEmpty(opponent, row-2, getColumn(column+2))) {
                opponent.getCheckers().remove(new Piece(row-1, getColumn(column+1)));
                this.getCheckers().remove(piece);
                this.getCheckers().add(piece4);
                return jump(opponent, piece4, x, y);
            }
        } else if (x==row+2 && y== column-2 && hasPlayers(opponent, row +1, getColumn(column -1)) &&
                isEmpty(opponent, row +2, getColumn(column -2))) {
            opponent.getCheckers().remove(new Piece(row+1, getColumn(column-1)));
            this.getCheckers().remove(piece);
            if (x == 7) {
                piece1.setKing(true);
            }
            this.getCheckers().add(piece1);
            return jump(opponent, piece1, x, y);
        } else if (x==row+2 && y== getColumn(column+2) && hasPlayers(opponent, row +1, getColumn(column +1)) &&
                isEmpty(opponent, row +2, getColumn(column +2))) {
            opponent.getCheckers().remove(new Piece(row+1, getColumn(column+1)));
            this.getCheckers().remove(piece);
            if (x == 7) {
                piece2.setKing(true);
            }
            this.getCheckers().add(piece2);
            return jump(opponent, piece2, x, y);
        }
        return new GameBoard(this, opponent, 1);
    }
}
