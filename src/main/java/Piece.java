public class Piece {
    private int row;
    private int column;
    private boolean isKing;

    Piece(int row,int column,boolean isKing){
            this.row =row;
            this.column =column;
            this.isKing =isKing;
    }

    public void makeKing(){
        this.isKing = true;
    }

}
