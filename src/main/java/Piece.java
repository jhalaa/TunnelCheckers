public class Piece {
    private int row;
    private int column;
    private boolean isKing;
    private PLAYER owner;

    Piece(int row,int column,boolean isKing, PLAYER owner){
            this.row =row;
            this.column =column;
            this.isKing =isKing;
            this.owner =owner;
    }

    public void MakeKing(){
        this.isKing = true;
    }

    public PLAYER getOwner() {
        return owner;
    }
}
