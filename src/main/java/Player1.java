import java.util.*;

/* This is the AI player */
public class Player1 extends APlayer {
    private static final int PLAYER_STARTING_ROW = 0;
    private static final int NUMBER_OF_COLUMNS = 8;

    private Set<Piece> checkers;

    Player1(){
        this.checkers = new HashSet<>();
        this.checkers.addAll(super.getPlayerPieces(PLAYER_STARTING_ROW));
    }

    public Set<Piece> getCheckers() {
        return this.checkers;
    }

    public Set<Piece> getKings() {
        return super.getKings(this.checkers);
    }

    public GameBoard move(APlayer opponent) {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        List<GameBoard> gameList = getAllValidMoves(opponent);

        List<Integer> heuristics = new ArrayList<>();
        if(gameList.isEmpty())
            return null;
        GameBoard tempBoard;

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
        int k = rand.nextInt(gameList.size());
        GameBoard board = gameList.get(k);
        return board;
    }

    public GameBoard makeMove(APlayer opponent, Piece piece, int x, int y) {
        GameBoard newBoard = new GameBoard();
        newBoard.getPlayer2().getCheckers().clear();
        newBoard.getPlayer1().getCheckers().clear();
        newBoard.getPlayer1().getCheckers().addAll(this.getCheckers());
        newBoard.getPlayer2().getCheckers().addAll(opponent.getCheckers());
        Piece newPiece = null;
        if (piece.isKing() || x == NUMBER_OF_COLUMNS - 1) {
            newPiece = new Piece(x, y, true);
        } else {
            newPiece = new Piece(x, y, false);
        }
        newBoard.getPlayer1().getCheckers().remove(piece);
        newBoard.getPlayer1().getCheckers().add(newPiece);
        newBoard.setCurrentTurn(1);
        return newBoard;
    }

    protected boolean isValidMove(APlayer opponent, Piece piece, int x, int y) {
        // without jumps

        if (hasPlayers(opponent, x, y) || !getCheckers().contains(piece) || hasPlayers(this, x, y)) {
            return false;
        }
        int preX = piece.getRow();
        int preY = piece.getColumn();
        if (piece.isKing()) {
            if (preX != 0 && x == preX - 1) {
                return y == getColumn(preY - 1) || y == getColumn(preY + 1);
            }
        }
        if (preX != 7 && x == preX + 1) {
            return y == getColumn(preY - 1) || y == getColumn(preY + 1);
        }

        return false;
    }

    public boolean isValidJump(APlayer opponent, Piece piece, int x, int y, int limit) {
        if(x < 0 || x > 7 || piece.getRow()<0 || piece.getRow()>7 || limit<0) {
            return false;
        }
        int row = piece.getRow();
        int column = piece.getColumn();
        Piece piece1 = new Piece(row+2,getColumn(column-2),piece.isKing());
        Piece piece2 = new Piece(row+2,getColumn(column+2),piece.isKing());
        if(piece.isKing() && x < row) {
            Piece piece3 = new Piece(row-2, getColumn(column-2), true);
            Piece piece4 = new Piece(row-2, getColumn(column+2), true);
            return x==row-2 && y==getColumn(column-2) && hasPlayers(opponent, row-1, getColumn(column-1)) &&
                    isEmpty(opponent, row-2, getColumn(column-2)) || isValidJump(opponent, piece3, x, y,limit-1)
                    || x==row-2 && y==getColumn(column+2) && hasPlayers(opponent, row-1,getColumn(column+1)) &&
                    isEmpty(opponent, row-2, getColumn(column+2)) || isValidJump(opponent, piece4, x, y,limit-1);
        }
        return x==row+2 && y== getColumn(column-2) && hasPlayers(opponent, row +1, getColumn(column -1)) &&
                isEmpty(opponent, row +2, getColumn(column -2)) || isValidJump(opponent,piece1,x,y,limit-1) ||
                x==row+2 && y== getColumn(column+2) && hasPlayers(opponent, row +1, getColumn(column +1)) &&
                        isEmpty(opponent, row +2, getColumn(column +2)) || isValidJump(opponent,piece2,x,y,limit-1);
    }

    public GameBoard jump(APlayer opponent, Piece piece, int x, int y) {
        //GameBoard newBoard = new GameBoard(this,opponent, 1);
        GameBoard newBoard = new GameBoard();
        newBoard.getPlayer2().getCheckers().clear();
        newBoard.getPlayer1().getCheckers().clear();
        newBoard.getPlayer1().getCheckers().addAll(this.getCheckers());
        newBoard.getPlayer2().getCheckers().addAll(opponent.getCheckers());
        newBoard.setCurrentTurn(1);
        int row = piece.getRow();
        int column = piece.getColumn();
        Piece piece1 = new Piece(row+2,getColumn(column-2),piece.isKing());
        Piece piece2 = new Piece(row+2,getColumn(column+2),piece.isKing());
        if(piece.isKing() && x < row) {
            Piece piece3 = new Piece(row-2, getColumn(column-2), true);
            Piece piece4 = new Piece(row-2, getColumn(column+2), true);
            if(newBoard.getPlayer1().isValidJump(newBoard.getPlayer2(),piece3,x,y,4) || x==row-2 && y==getColumn(column-2) && hasPlayers(opponent, row-1, getColumn(column-1)) &&
                    isEmpty(opponent, row-2, getColumn(column-2))) {
                newBoard.getPlayer2().getCheckers().remove(new Piece(row-1,getColumn(column-1)));
                newBoard.getPlayer1().getCheckers().remove(piece);
                newBoard.getPlayer1().getCheckers().add(piece3);
                return newBoard.getPlayer1().jump(newBoard.getPlayer2(), piece3, x, y);
            }
            if (newBoard.getPlayer1().isValidJump(newBoard.getPlayer2(),piece4,x,y,4) || x==row-2 && y==getColumn(column+2) && hasPlayers(opponent, row-1,getColumn(column+1)) &&
                    isEmpty(opponent, row-2, getColumn(column+2))) {
                newBoard.getPlayer2().getCheckers().remove(new Piece(row-1, getColumn(column+1)));
                newBoard.getPlayer1().getCheckers().remove(piece);
                newBoard.getPlayer1().getCheckers().add(piece4);
                return newBoard.getPlayer1().jump(newBoard.getPlayer2(), piece4, x, y);
            }
        } else if (newBoard.getPlayer1().isValidJump(newBoard.getPlayer2(),piece1,x,y,4) || x==row+2 && y== getColumn(column-2) && hasPlayers(opponent, row +1, getColumn(column -1)) &&
                isEmpty(opponent, row +2, getColumn(column -2))) {
            newBoard.getPlayer2().getCheckers().remove(new Piece(row+1, getColumn(column-1)));
            newBoard.getPlayer1().getCheckers().remove(piece);
            if (x == 7) {
                piece1.setKing(true);
            }
            newBoard.getPlayer1().getCheckers().add(piece1);
            return newBoard.getPlayer1().jump(newBoard.getPlayer2(), piece1, x, y);
        } else if (newBoard.getPlayer1().isValidJump(newBoard.getPlayer2(),piece2,x,y,4) || x==row+2 && y== getColumn(column+2) && hasPlayers(opponent, row +1, getColumn(column +1)) &&
                isEmpty(opponent, row +2, getColumn(column +2))) {
            newBoard.getPlayer2().getCheckers().remove(new Piece(row+1, getColumn(column+1)));
            newBoard.getPlayer1().getCheckers().remove(piece);
            if (x == 7) {
                piece2.setKing(true);
            }
            newBoard.getPlayer1().getCheckers().add(piece2);
            return newBoard.getPlayer1().jump(newBoard.getPlayer2(), piece2, x, y);
        }
        newBoard.getPlayer1().getCheckers().remove(piece);
//        if (x == 7) {
//            piece.setKing(true);
//        }
        newBoard.getPlayer1().getCheckers().add(new Piece(x,y,piece.isKing()));
        return newBoard;
    }
}
