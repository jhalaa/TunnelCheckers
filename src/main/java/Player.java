import java.util.List;
import java.util.Set;

public interface Player {
    String getPlayerName();
    GameBoard move(APlayer opponent);
    Set<Piece> getCheckers();
    Set<Piece> getKings();
}
