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

        long timeUsed = System.currentTimeMillis() - this.start;

        if(timeUsed > (this.playclock - 500)) {
            System.out.println("Throwing exception!");
            //throw new OutOfTimeException("Out of time!");
        }

        if(state.TerminalTest() || depth <= 0) {
            bestMove.setScore(state.eval());
            bestMove.setMove(state.getLastMove());
            return bestMove;
        }

        List<Integer> actions = state.LegalMoves();
        //Collections.shuffle(actions);

        for(Integer action : actions) {
            //reply = AlphaBeta(depth - 1, state.ResultingState(action), -beta, -alpha);

            state.DoMove(action);
            reply = AlphaBeta(depth - 1, state, -beta, -alpha);
            reply.setScore(-reply.getScore());
            state.UndoMove(action);

            System.out.println("depth: " + depth + ", value: " + reply.getScore() + ", move: (" + (action + 1) + "), alpha: " + alpha + ", beta: " + beta);
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

    /*public int AlphaBetaRoot(int depth, State state, int alpha, int beta) throws OutOfTimeException {
        int bestCol = Integer.MIN_VALUE + 1;

        long timeUsed = System.currentTimeMillis() - this.start;

        if(timeUsed > (this.playclock - 500)) {
            System.out.println("Throwing exception!");
            throw new OutOfTimeException("Out of time!");
        }

        if(state.TerminalTest() || depth <= 0) {
            bestCol = state.getLastMove();
            return bestCol;
        }

        int bestValue = Integer.MIN_VALUE + 1;
        int value;

        List<Integer> actions = state.LegalMoves();
        Collections.shuffle(actions);

        for(Integer action : actions) {
            //reply = AlphaBeta(depth - 1, state.ResultingState(action), -beta, -alpha);

            state.DoMove(action);
            value = -AlphaBeta(depth - 1, state, -beta, -alpha);
            state.UndoMove(action);

            //System.out.println("depth: " + depth + ", value: " + reply.getScore() + ", move: (" + (action + 1) + ")");
            if(value > bestValue) {
                bestCol = action + 1;
                bestValue = value;
            }

            if(bestValue > alpha) {
                alpha = bestValue;
            }

            if(alpha >= beta) break;
        }

        return bestCol;
    }

    public int AlphaBeta(int depth, State state, int alpha, int beta) throws OutOfTimeException {
        long timeUsed = System.currentTimeMillis() - this.start;

        if(timeUsed > (this.playclock - 500)) {
            System.out.println("Throwing Exception!");
            throw new OutOfTimeException("Out of time!");
        }

        if(state.TerminalTest() || depth <= 0) {
            return state.eval();
        }

        int bestValue = Integer.MIN_VALUE + 1;
        int value;

        List<Integer> actions = state.LegalMoves();
        Collections.shuffle(actions);

        for(Integer action : actions) {
            state.DoMove(action);
            value = -AlphaBeta(depth - 1, state, -beta, -alpha);
            state.UndoMove(action);

            bestValue = Math.max(value, bestValue);

            if(bestValue > alpha) {
                alpha = bestValue;
            }

            if(alpha >= beta) {
                break;
            }
        }

        return bestValue;
    }*/

}
