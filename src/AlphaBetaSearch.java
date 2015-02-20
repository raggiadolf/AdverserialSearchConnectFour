//package AdverserialSearchConnectFour;

/**
 * Created by ragnaradolf on 16/02/15.
 */
public class AlphaBetaSearch {

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
}
