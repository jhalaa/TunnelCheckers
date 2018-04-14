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

        if (isValidMove(opponent, new Piece(row, column), newRow, newColumn)) {
            Iterator<Piece> iterator = checkers.iterator();
            while (iterator.hasNext()) {
                Piece peice = iterator.next();
                if (peice.getRow() == row && peice.getColumn() == column) {
                    checkers.remove(peice);
                    checkers.add(new Piece(newRow, newColumn, peice.isKing()));
                    return new GameBoard(this, opponent, 0);
                }
            }
        } else {
            System.out.println("Not a valid move!Please play again");
            return this.move(opponent);
        }
        return null;
    }


    public boolean isValidJump(APlayer opponent, Piece piece, int x, int y) {
        if (x < 0 || x > 7) {
            return false;
        }
        int row = piece.getRow();
        int column = piece.getColumn();
        Piece piece1 = new Piece(row - 2, getColumn(column - 2), piece.isKing());
        Piece piece2 = new Piece(row - 2, getColumn(column + 2), piece.isKing());
        if (piece.isKing() && x > row) {
            Piece piece3 = new Piece(row + 2, getColumn(column - 2), true);
            Piece piece4 = new Piece(row + 2, getColumn(column + 2), true);
            return x == row + 2 && y == column - 2 && hasPlayers(opponent, row + 1, getColumn(column - 1)) &&
                    isEmpty(opponent, row + 2, getColumn(column - 2)) || isValidJump(opponent, piece3, x, y)
                    || x == row + 2 && y == getColumn(column + 2) && hasPlayers(opponent, row + 1, getColumn(column + 1)) &&
                    isEmpty(opponent, row + 2, getColumn(column + 2)) || isValidJump(opponent, piece4, x, y);
        }
        return x == row - 2 && y == column - 2 && hasPlayers(opponent, row - 1, getColumn(column - 1)) &&
                isEmpty(opponent, row - 2, getColumn(column - 2)) || isValidJump(opponent, piece1, x, y) ||
                x == row - 2 && y == getColumn(column + 2) && hasPlayers(opponent, row - 1, getColumn(column + 1)) &&
                        isEmpty(opponent, row - 2, getColumn(column + 2)) || isValidJump(opponent, piece2, x, y);
    }

    public GameBoard makeMove(APlayer opponent, Piece piece, int x, int y) {
        Piece newPiece = null;
        if (piece.isKing() || x == 0) {
            newPiece = new Piece(x, y, true);
        } else {
            newPiece = new Piece(x, y, false);
        }
        getCheckers().remove(piece);
        getCheckers().add(newPiece);
        return new GameBoard(this, opponent, 0);
    }

    public GameBoard jump(APlayer opponent, Piece piece, int x, int y) {
        int row = piece.getRow();
        int column = piece.getColumn();
        Piece piece1 = new Piece(row - 2, getColumn(column - 2), piece.isKing());
        Piece piece2 = new Piece(row - 2, getColumn(column + 2), piece.isKing());
        if (piece.isKing() && x > row) {
            Piece piece3 = new Piece(row + 2, getColumn(column - 2), true);
            Piece piece4 = new Piece(row + 2, getColumn(column + 2), true);
            if (x == row + 2 && y == column - 2 && hasPlayers(opponent, row + 1, getColumn(column - 1)) &&
                    isEmpty(opponent, row + 2, getColumn(column - 2))) {
                opponent.getCheckers().remove(new Piece(row + 1, getColumn(column - 1)));
                this.getCheckers().remove(piece);
                this.getCheckers().add(piece3);
                return jump(opponent, piece3, x, y);
            }
            if (x == row + 2 && y == getColumn(column + 2) && hasPlayers(opponent, row + 1, getColumn(column + 1)) &&
                    isEmpty(opponent, row + 2, getColumn(column + 2))) {
                opponent.getCheckers().remove(new Piece(row + 1, getColumn(column + 1)));
                this.getCheckers().remove(piece);
                this.getCheckers().add(piece4);
                return jump(opponent, piece4, x, y);
            }
        } else if (x == row - 2 && y == column - 2 && hasPlayers(opponent, row - 1, getColumn(column - 1)) &&
                isEmpty(opponent, row - 2, getColumn(column - 2))) {
            opponent.getCheckers().remove(new Piece(row - 1, getColumn(column - 1)));
            this.getCheckers().remove(piece);
            if (x == 0) {
                piece1.setKing(true);
            }
            this.getCheckers().add(piece1);
            return jump(opponent, piece1, x, y);
        } else if (x == row - 2 && y == getColumn(column + 2) && hasPlayers(opponent, row - 1, getColumn(column + 1)) &&
                isEmpty(opponent, row - 2, getColumn(column + 2))) {
            opponent.getCheckers().remove(new Piece(row - 1, getColumn(column + 1)));
            this.getCheckers().remove(piece);
            if (x == 0) {
                piece2.setKing(true);
            }
            this.getCheckers().add(piece2);
            return jump(opponent, piece2, x, y);
        }
        return new GameBoard(this, opponent, 0);
    }


    protected boolean isValidMove(APlayer opponent, Piece piece, int x, int y) {
        // there is no pawn diagonally opposite and the checker can be moved
        if (hasPlayers(opponent, x, y) || !getCheckers().contains(piece)) {
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