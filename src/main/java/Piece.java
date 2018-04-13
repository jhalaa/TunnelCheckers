public class Piece {
    private int row;
    private int column;
    private boolean isKing;

    Piece(int row,int column,boolean isKing){
            this.row =row;
            this.column =column;
            this.isKing =isKing;
    }

    Piece(int row,int column){
        this.row = row;
        this.column=column;
    }

    public void makeKing(){
        this.isKing = true;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setKing(boolean king) {
        isKing = king;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;

        if (row != piece.row) return false;
        return column == piece.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }
}
