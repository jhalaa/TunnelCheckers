import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GameBoard {
    private Set<Piece> players;

    GameBoard(){
        players = new HashSet<>();
        players.addAll(getPlayer1Pieces());
        players.addAll(getPlayer2Pieces());
    }

    private Set<Piece> getPlayer1Pieces() {
        Set<Piece> players = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            int j = i % 2 == 0 ? 0 : 1;
            while (j < 8) {
                Piece piece = new Piece(i, j, false, PLAYER.ONE);
                players.add(piece);
                j += 2;
            }
        }
        return players;
    }

    private Set<Piece> getPlayer2Pieces() {
        Set<Piece> players = new HashSet<>();
        for (int i = 5; i < 9; i++) {
            int j = i % 2 == 0 ? 1 : 0;
            while (j < 8) {
                Piece piece = new Piece(i, j, false, PLAYER.TWO);
                players.add(piece);
                j += 2;
            }
        }
        return players;
    }

    void deletePiece(Piece piece){
        this.players.remove(piece);
    }

    boolean checkWin(){
        return players.stream().allMatch(player -> player.getOwner().equals(PLAYER.ONE)) ||
                players.stream().allMatch(player -> player.getOwner().equals(PLAYER.TWO));
    }

}
