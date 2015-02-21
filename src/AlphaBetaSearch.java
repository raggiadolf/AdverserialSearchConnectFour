//package AdverserialSearchConnectFour;

/**
 * Created by ragnaradolf on 16/02/15.
 */
public class AlphaBetaSearch {

    /*
    public static int AlphaBeta(int depth, State state, int alpha, int beta) {
        int bestValue, value;
        if(state.TerminalTest() || depth <= 0) {
            return state.eval();
        }
        bestValue = (int)Double.NEGATIVE_INFINITY;

        for(String action : state.LegalMoves()) {
            value = -AlphaBeta(depth - 1, state.ResultingState(action), -beta, -alpha);

            bestValue = Math.max(value, bestValue);
            if(bestValue > alpha) {
                alpha = bestValue;
                if(alpha >= beta) break;
            }
        }

        return bestValue;
    }
    */

    public static Node AlphaBeta(int depth, State state, int alpha, int beta) {
        Node bestMove = new Node();
        Node reply;

        if(state.TerminalTest() || depth <= 0) {
            bestMove.setScore(state.eval());
            bestMove.setMove(state.getLastMove());
            return bestMove;
        }

        bestMove.setScore(Integer.MIN_VALUE); /* Already done in the constructor? */

        for(String action : state.LegalMoves()) {
            reply = AlphaBeta(depth - 1, state.ResultingState(action), -beta, -alpha);
            reply.setScore(-reply.getScore());

            bestMove.setScore(Math.max(reply.getScore(), bestMove.getScore()));
            bestMove.setMove(action);

            if(bestMove.getScore() > alpha) {
                alpha = bestMove.getScore();
                if(alpha >= beta) break;
            }
        }

        return bestMove;
    }

}
