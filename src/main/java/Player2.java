import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

public class Player2 implements Player {
    static final int PLAYER_STARTING_ROW = 5;
    static final int NUMBER_OF_COLUMNS = 8;
    private Set<Piece> checkers;
    private Set<Piece> kings;

    Player2(){
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
        return "Player2";
    }

    public boolean move() {

        return false;
    }



}
