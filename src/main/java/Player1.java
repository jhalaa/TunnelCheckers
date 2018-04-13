import java.util.*;

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

    public boolean move(Player opponent) {
        return false;
    }


    public List<GameBoard> getAllValidMoves(Player opponent) {
        List<GameBoard> res = new ArrayList<>();
        for (Piece checker : getCheckers()) {
            int row = checker.getRow();
            int column = checker.getColumn();
            List<int[]> adjacent = getAdjacent(row, column);
            for (int[] array : adjacent) {
                int x = array[0];
                int y = array[1];
                if(isValidMove(opponent, checker, x, y)) {
                    GameBoard newBoard = makeMove(opponent, checker, x, y);
                    res.add(newBoard);
                }
            }
        }

        res.addAll(getValidJumps(opponent));
        return res;
    }

    private List<int[]> getAdjacent(int x, int y) {
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

    public GameBoard makeJump(Player opponent, Piece piece, int x, int y) {
        if(isValidJump(opponent,piece,x,y)){
            // kill the oponent
            // and move to new position
        }
    }

    public List<GameBoard> getValidJumps(Player opponent) {
        List<GameBoard> gameList = new ArrayList<>();
        for(Piece piece : this.checkers){
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(isValidJump(opponent,piece,i,j)){

                    }

                }
            }
        }
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
        // without jumps

        if (hasPlayers(opponent, x, y) || !getCheckers().contains(piece)) {
            return false;
        }
        int preX = piece.getRow();
        int preY = piece.getColumn();
        if (getKings().contains(piece)) {
            if (preX != 0 && x == preX - 1) {
                return y == getColumn(preY - 1) || y == getColumn(preY + 1);
            }
        }
        if (x == preX + 1) {
            return y == getColumn(preY - 1) || y == getColumn(preY + 1);
        }

        //with jumps
        isValidJump(opponent,piece,x,y);

        return false;
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

    private int getColumn(int columnNum){
        if(columnNum<0)
            return 8+columnNum;
        else if(columnNum>7)
            return columnNum%8;
        else
            return columnNum;
    }

    public boolean isValidJump(Player opponent, Piece piece, int x, int y) {
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

    public GameBoard jump(Player opponent, Piece piece, int x, int y) {
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
            }

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
}
