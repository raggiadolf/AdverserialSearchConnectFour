//package AdverserialSearchConnectFour;

import java.util.Collections;
import java.util.List;

public class AlphaBetaSearch {


    /*
    public static int AlphaBeta(int depth, State state, int alpha, int beta) {
        int bestValue, value;
        if(state.TerminalTest() || depth <= 0) {
            return state.eval();
        }
        bestValue = Integer.MIN_VALUE + 1;

        Collection<Integer> actions = state.LegalMoves();

        for(Integer action : actions) {
            value = -AlphaBeta(depth - 1, state.ResultingState(action), -beta, -alpha);

            bestValue = Math.max(value, bestValue);
            if(bestValue > alpha) {
                alpha = bestValue;
            }
            if(alpha >= beta) break;
        }

        return bestValue;
    }

    public String GetBestMove(int depth, State state, int alpha, int beta) {
        int Value = AlphaBeta(depth, state, alpha, beta);
        return null;
    }
    */

    public static Node AlphaBetaSearch(int depth, State state) {
        return MaxValue(depth, state, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1);
    }

    public static Node MaxValue(int depth, State state, int alpha, int beta){
        Node bestMove = new Node();
        Node reply;

        if(state.TerminalTest() || depth <= 0) {
            bestMove.setScore(state.eval());
            bestMove.setMove(state.getLastMove());
            return bestMove;
        }

        bestMove.setScore(Integer.MIN_VALUE + 1);

        List<Integer> actions = state.LegalMoves();

        for(Integer action : actions) {
            reply = MinValue(depth - 1, state.ResultingState(action), alpha, beta);

            if(reply.getScore() > bestMove.getScore()) {
                bestMove.setScore(reply.getScore());
                bestMove.setMove(reply.getMove());
            }

            if(bestMove.getScore() >= beta) {
                return bestMove;
            }

            alpha = Math.max(alpha, bestMove.getScore());
        }
        return bestMove;
    }

    public static Node MinValue(int depth, State state, int alpha, int beta) {
        Node bestMove = new Node();
        Node reply;

        if(state.TerminalTest() || depth <= 0) {
            bestMove.setScore(state.eval());
            bestMove.setMove(state.getLastMove());
            return bestMove;
        }

        bestMove.setScore(Integer.MAX_VALUE - 1);

        List<Integer> actions = state.LegalMoves();

        for(Integer action : actions) {
            reply = MaxValue(depth - 1, state.ResultingState(action), alpha, beta);

            if(reply.getScore() < bestMove.getScore()) {
                bestMove.setScore(reply.getScore());
                bestMove.setMove(reply.getMove());
            }

            if(bestMove.getScore() <= alpha) {
                return bestMove;
            }

            beta = Math.max(beta, bestMove.getScore());
        }
        return bestMove;
    }

    /*
    public static Node AlphaBeta(int depth, State state, int alpha, int beta, boolean player) {
        Node bestMove = new Node();
        Node reply;

        List<Integer> actions = state.LegalMoves();

        Collections.shuffle(actions);

        if(player) {

            if(state.TerminalTest() || depth <= 0) {
                bestMove.setScore(state.eval());
                bestMove.setMove(state.getLastMove());
                return bestMove;
            }

            bestMove.setScore(Integer.MIN_VALUE + 1);

            for(Integer action : actions) {
                reply = AlphaBeta(depth - 1, state.ResultingState(action), alpha, beta, false);

                if(reply.getScore() > bestMove.getScore()) {
                    bestMove.setMove(reply.getMove());
                    bestMove.setScore(reply.getScore());
                }

                if(bestMove.getScore() >= beta) {
                    return bestMove;
                }

                alpha = Math.max(alpha, bestMove.getScore());
            }
        } else {
            if(state.TerminalTest() || depth <= 0) {
                bestMove.setScore(state.eval());
                bestMove.setMove(state.getLastMove());
                return bestMove;
            }

            bestMove.setScore(Integer.MAX_VALUE - 1);

            for(Integer action : actions) {
                reply = AlphaBeta(depth - 1, state.ResultingState(action), alpha, beta, true);

                if(reply.getScore() < bestMove.getScore()) {
                    bestMove.setMove(reply.getMove());
                    bestMove.setScore(reply.getScore());
                }

                if(bestMove.getScore() <= alpha) {
                    return bestMove;
                }

                beta = Math.min(beta, bestMove.getScore());
            }
        }

        return bestMove;
    }
    */



    public Node AlphaBeta(int depth, State state, int alpha, int beta) {
        Node bestMove = new Node();
        Node reply;

        if(state.TerminalTest() || depth <= 0) {
            bestMove.setScore(state.eval());
            bestMove.setMove(state.getLastMove());
            return bestMove;
        }

        List<Integer> actions = state.LegalMoves();

        // TODO: Try to find the 'best' action instead of just shuffling.

        Collections.shuffle(actions);

        for(Integer action : actions) {
            reply = AlphaBeta(depth - 1, state.ResultingState(action), -beta, -alpha);
            reply.setScore(-reply.getScore());

            if(reply.getScore() > bestMove.getScore()) {
                bestMove.setMove(reply.getMove());
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
