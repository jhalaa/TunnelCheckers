import java.util.List;
import java.util.Set;

public interface Player {
    String getPlayerName();
    GameBoard move(Player opponent);
    Set<Piece> getCheckers();
    Set<Piece> getKings();
    List<GameBoard> getAllValidMoves(Player opponent);
    int minimax(Player opponent, int depth, int alpha, int beta, int currentTurn);
}
