//package AdverserialSearchConnectFour;

import java.util.Collections;
import java.util.List;

public class AlphaBetaSearch{

    /* "You probably have a very subtle bug in your code." */

    private int playclock;
    private long start;


    public AlphaBetaSearch(int playclock){
        this.playclock = playclock * 1000;
        this.start = (System.currentTimeMillis());
    }

    public Node AlphaBeta(int depth, State state, int alpha, int beta) throws OutOfTimeException {
        Node bestMove = new Node();
        Node reply;

        long timeUsed = (System.currentTimeMillis()) - this.start;

        if(timeUsed > (this.playclock - 500)) {
            System.out.println("Throwing exception!");
            throw new OutOfTimeException("Out of time!");
        }

        if(state.TerminalTest() || depth <= 0) {
            bestMove.setScore(state.eval());
            bestMove.setMove(state.getLastMove());
            return bestMove;
        }

        List<Integer> actions = state.LegalMoves();
        Collections.shuffle(actions);

        for(Integer action : actions) {
            reply = AlphaBeta(depth - 1, state.ResultingState(action), -beta, -alpha);
            reply.setScore(-reply.getScore());

            //System.out.println("depth: " + depth + ", value: " + reply.getScore() + ", move: (" + (action + 1) + ")");
            if(reply.getScore() > bestMove.getScore()) {
                bestMove.setMove("(DROP " + (action + 1) + ")");
                bestMove.setScore(reply.getScore());
            }

            if(bestMove.getScore() > alpha) {
                alpha = bestMove.getScore();
            }

            if(alpha >= beta) break;
        }

        return bestMove;
    }

}
