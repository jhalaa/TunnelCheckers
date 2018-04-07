import java.util.Set;

public interface Player {
    String getPlayerName();
    boolean move();
    Set<Piece> getCheckers();
    Set<Piece> getKings();
}
