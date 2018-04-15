import java.util.*;

/* This is the human player */
public class Player2 extends APlayer {
    private static final int PLAYER_STARTING_ROW = 5;
    private Set<Piece> checkers;

    Player2() {
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

        Piece piece = getThePeice(row,column);
        if (piece != null && isValidMove(opponent, piece, newRow, newColumn)) {
            checkers.remove(piece);
            if(newRow == 0) {
                piece.setKing(true);
            }
            checkers.add(new Piece(newRow, newColumn, piece.isKing()));
            return new GameBoard(opponent, this, 0);
        }
        else if(piece!=null && isValidJump(opponent,piece, newRow, newColumn,4)) {
            return jump(opponent,new Piece(row, column), newRow, newColumn);
        }
        else {
            System.out.println("Not a valid move!Please play again");
            return this.move(opponent);
        }
    }

    private Piece getThePeice(int row, int column) {
        Iterator<Piece> p = this.getCheckers().iterator();
        while(p.hasNext()){
            Piece piece = p.next();
            if(piece.equals(new Piece(row,column)))
                return piece;
        }
        return null;
    }


    public boolean isValidJump(APlayer opponent, Piece piece, int x, int y, int limit) {
        if(x < 0 || x > 7 || piece.getRow()<0 || piece.getRow()>7 || limit<0) {
            return false;
        }
        int row = piece.getRow();
        int column = piece.getColumn();
        Piece piece1 = new Piece(row - 2, getColumn(column - 2), piece.isKing());
        Piece piece2 = new Piece(row - 2, getColumn(column + 2), piece.isKing());
        Piece piece3 = new Piece(row + 2, getColumn(column - 2), true);
        Piece piece4 = new Piece(row + 2, getColumn(column + 2), true);
        if(isValidSingleJump1(opponent, x, y, row, column) || isValidSingleJump2(opponent, x, y, row, column))
            return true;
        else if(isValidSingleJump1(opponent, row-2, column-2, row, column) && isValidJump(opponent,piece1,x,y,limit-1))
            return true;
        else if(isValidSingleJump2(opponent, row-2, column+2, row, column) && isValidJump(opponent,piece2,x,y,limit-1))
            return true;
        else if(piece.isKing() && x > row) {
            if(isValidSingleJump3(opponent, x, y, row, column) || isValidSingleJump4(opponent, x, y, row, column))
                return true;
            else if(isValidSingleJump1(opponent, row+2, column-2, row, column) && isValidJump(opponent,piece3,x,y,limit-1))
                return true;
            else if(isValidSingleJump2(opponent, row+2, column+2, row, column) && isValidJump(opponent,piece4,x,y,limit-1))
                return true;
        }
        return false;
    }

    private boolean isValidSingleJump4(APlayer opponent, int x, int y, int row, int column) {
        return x == row + 2 && y == getColumn(column + 2) && hasPlayers(opponent, row + 1, getColumn(column + 1)) &&
        isEmpty(opponent, row + 2, getColumn(column + 2));
    }

    private boolean isValidSingleJump3(APlayer opponent, int x, int y, int row, int column) {
        return x == row + 2 && y == getColumn(column - 2) && hasPlayers(opponent, row + 1, getColumn(column - 1)) &&
                isEmpty(opponent, row + 2, getColumn(column - 2));
    }

    private boolean isValidSingleJump2(APlayer opponent, int x, int y, int row, int column) {
        return x == row - 2 && y == getColumn(column + 2) && hasPlayers(opponent, row - 1, getColumn(column + 1)) &&
                isEmpty(opponent, row - 2, getColumn(column + 2));
    }

    private boolean isValidSingleJump1(APlayer opponent, int x, int y, int row, int column) {
        return x == row - 2 && y == getColumn(column - 2)
                && hasPlayers(opponent, row - 1, getColumn(column - 1))
                && isEmpty(opponent, row - 2, getColumn(column - 2));
    }

//    private boolean hasNoPieceAround(Piece piece) {
//        int row = piece.getRow();
//        int col = piece.getColumn();
//        return
//    }

    public GameBoard makeMove(APlayer opponent, Piece piece, int x, int y) {
        GameBoard newBoard = new GameBoard();
        newBoard.getPlayer2().getCheckers().clear();
        newBoard.getPlayer1().getCheckers().clear();
        newBoard.getPlayer2().getCheckers().addAll(this.getCheckers());
        newBoard.getPlayer1().getCheckers().addAll(opponent.getCheckers());
        Piece newPiece = null;
        if (piece.isKing() || x == 0) {
            newPiece = new Piece(x, y, true);
        } else {
            newPiece = new Piece(x, y, false);
        }
        newBoard.getPlayer2().getCheckers().remove(piece);
        newBoard.getPlayer2().getCheckers().add(newPiece);
        newBoard.setCurrentTurn(0);
        return newBoard;
    }

    public GameBoard jump(APlayer opponent, Piece piece, int x, int y) {
        //GameBoard newBoard = new GameBoard(opponent, this, 0);
        GameBoard newBoard = new GameBoard();
        newBoard.getPlayer2().getCheckers().clear();
        newBoard.getPlayer1().getCheckers().clear();
        newBoard.getPlayer2().getCheckers().addAll(this.getCheckers());
        newBoard.getPlayer1().getCheckers().addAll(opponent.getCheckers());
        newBoard.setCurrentTurn(0);
        int row = piece.getRow();
        int column = piece.getColumn();
        Piece piece1 = new Piece(row - 2, getColumn(column - 2), piece.isKing());
        Piece piece2 = new Piece(row - 2, getColumn(column + 2), piece.isKing());
        if (piece.isKing() && x > row) {
            Piece piece3 = new Piece(row + 2, getColumn(column - 2), true);
            Piece piece4 = new Piece(row + 2, getColumn(column + 2), true);
            if (newBoard.getPlayer2().isValidJump(newBoard.getPlayer1(),piece3,x,y,4) || x == row + 2 && y == getColumn(column - 2) && hasPlayers(opponent, row + 1, getColumn(column - 1)) &&
                    isEmpty(opponent, row + 2, getColumn(column - 2))) {
                newBoard.getPlayer1().getCheckers().remove(new Piece(row + 1, getColumn(column - 1)));
                newBoard.getPlayer2().getCheckers().remove(piece);
                newBoard.getPlayer2().getCheckers().add(piece3);
                return newBoard.getPlayer2().jump(newBoard.getPlayer1(), piece3, x, y);
            }
            if (newBoard.getPlayer2().isValidJump(newBoard.getPlayer1(),piece4,x,y,4) || x == row + 2 && y == getColumn(column + 2) && hasPlayers(opponent, row + 1, getColumn(column + 1)) &&
                    isEmpty(opponent, row + 2, getColumn(column + 2))) {
                newBoard.getPlayer1().getCheckers().remove(new Piece(row + 1, getColumn(column + 1)));
                newBoard.getPlayer2().getCheckers().remove(piece);
                newBoard.getPlayer2().getCheckers().add(piece4);
                return newBoard.getPlayer2().jump(newBoard.getPlayer1(), piece4, x, y);
            }
        } else if (newBoard.getPlayer2().isValidJump(newBoard.getPlayer1(),piece1,x,y,4) || x == row - 2 && y == getColumn(column - 2) && hasPlayers(opponent, row - 1, getColumn(column - 1)) &&
                isEmpty(opponent, row - 2, getColumn(column - 2))) {
            newBoard.getPlayer1().getCheckers().remove(new Piece(row - 1, getColumn(column - 1)));
            newBoard.getPlayer2().getCheckers().remove(piece);
            if (x == 0) {
                piece1.setKing(true);
            }
            newBoard.getPlayer2().getCheckers().add(piece1);
            return newBoard.getPlayer2().jump(newBoard.getPlayer1(), piece1, x, y);
        } else if (newBoard.getPlayer2().isValidJump(newBoard.getPlayer1(),piece2,x,y,4) || x == row - 2 && y == getColumn(column + 2) && hasPlayers(opponent, row - 1, getColumn(column + 1)) &&
                isEmpty(opponent, row - 2, getColumn(column + 2))) {
            newBoard.getPlayer1().getCheckers().remove(new Piece(row - 1, getColumn(column + 1)));
            newBoard.getPlayer2().getCheckers().remove(piece);
            if (x == 0) {
                piece2.setKing(true);
            }
            newBoard.getPlayer2().getCheckers().add(piece2);
            return newBoard.getPlayer2().jump(newBoard.getPlayer1(), piece2, x, y);
        }
        newBoard.getPlayer2().getCheckers().remove(piece);
        if (x == 0) {
            piece.setKing(true);
        }
        newBoard.getPlayer2().getCheckers().add(new Piece(x,y,piece.isKing()));
        return newBoard;
    }


    protected boolean isValidMove(APlayer opponent, Piece piece, int x, int y) {
        // there is no pawn diagonally opposite and the checker can be moved
        if (hasPlayers(opponent, x, y) || !getCheckers().contains(piece) || hasPlayers(this, x, y)) {
            return false;
        }
        int preX = piece.getRow();
        int preY = piece.getColumn();
        if (piece.isKing()) {
            if (preX != 7 && x == preX + 1) {
                return y == getColumn(preY - 1) || y == getColumn(preY + 1);
            }
        }
        if (preX != 0 && x == preX - 1) {
            return y == getColumn(preY - 1) || y == getColumn(preY + 1);
        }

        return false;
    }

}