/**
 * Created by yingchen on 2018/4/10.
 */
public class HeuristicCreater {

    static final int KING_WEIGHT = 60;
    static final int PIECE_WEIGHT = 50;

    public static int HeuristicFunction(GameBoard game) {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        int numberOfTurns = game.getNumberOfTurns();
        int player1Kings = player1.getKings().size();
        int player2Kings = player2.getKings().size();
        int player1NormalPieces = player1.getCheckers().size() - player1Kings;
        int player2NormalPieces = player2.getCheckers().size() - player2Kings;
        if(numberOfTurns++ %2==0) {
            return player1Kings * KING_WEIGHT + player1NormalPieces * PIECE_WEIGHT
                    - player2Kings * KING_WEIGHT - player2NormalPieces * PIECE_WEIGHT;
        } else {
            return player2Kings * KING_WEIGHT + player2NormalPieces * PIECE_WEIGHT
                    - player1Kings * KING_WEIGHT - player1NormalPieces * PIECE_WEIGHT;
        }
    }
}
