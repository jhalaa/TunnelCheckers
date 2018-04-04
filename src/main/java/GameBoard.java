import java.util.HashSet;
import java.util.Set;

public class GameBoard {
    private Set<Piece> players;
    private PLAYER winner;
    private PLAYER currentPlayer;

    static final int PLAYER1_STARTING_ROW = 0;
    static final int PLAYER2_STARTING_ROW = 5;

    public GameBoard(){
        currentPlayer = PLAYER.ONE;
        players = new HashSet<>();
        players.addAll(getPlayerPieces(PLAYER.ONE,PLAYER1_STARTING_ROW));
        players.addAll(getPlayerPieces(PLAYER.TWO,PLAYER2_STARTING_ROW));
    }

    private Set<Piece> getPlayerPieces(PLAYER player, int start) {
        Set<Piece> players = new HashSet<>();
        for (int i = start; i < start+3; i++) {
            int j = i % 2 == 0 ? 1 : 0;
            while (j < 8) {
                Piece piece = new Piece(i, j, false, player);
                players.add(piece);
                j += 2;
            }
        }
        return players;
    }

    void deletePiece(Piece piece){
        this.players.remove(piece);
    }

    public PLAYER getWinner(){
        return this.winner;
    }

    public boolean checkWin(){
        if(players.stream().allMatch(player -> player.getOwner().equals(PLAYER.ONE)))
            winner=PLAYER.ONE;
        if(players.stream().allMatch(player -> player.getOwner().equals(PLAYER.TWO)))
            winner=PLAYER.TWO;
        return this.winner!=null;
    }

    public void play() {

    }

    public void toggleCurrentPlayer() {
        this.currentPlayer = this.currentPlayer==PLAYER.ONE?PLAYER.TWO:PLAYER.ONE;
    }
}
