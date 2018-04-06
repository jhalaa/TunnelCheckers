import java.util.HashSet;
import java.util.Set;

public class Player1 implements Player {
    static final int PLAYER_STARTING_ROW = 0;
    static final int NUMBER_OF_COLUMNS = 8;

    private Set<Piece> checkers;

    Player1(){
        this.checkers.addAll(getPlayerPieces());
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

    public String getPlayerName() {
        return "Player1";
    }

    public void move() {
        return;
    }


}
