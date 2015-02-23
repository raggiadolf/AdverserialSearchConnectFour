//package AdverserialSearchConnectFour;

import java.util.Collections;
import java.util.List;

public class AlphaBetaSearch {

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

            System.out.println("I am MaxValue, depth: " + depth + ", value: " + reply.getScore() + ", move: " + reply.getMove());

            if(reply.getScore() >= bestMove.getScore()) {
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

            System.out.println("I am MinValue, depth: " + depth + ", value: " + reply.getScore() + ", move: " + reply.getMove());

            if(reply.getScore() <= bestMove.getScore()) {
                bestMove.setScore(reply.getScore());
                bestMove.setMove(reply.getMove());
            }

            if(bestMove.getScore() <= alpha) {
                return bestMove;
            }

            beta = Math.min(beta, bestMove.getScore());
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



    public static Node AlphaBeta(int depth, State state, int alpha, int beta) {
        Node bestMove = new Node();
        Node reply;

        if(state.TerminalTest() || depth <= 0) {
            bestMove.setScore(state.eval());
            bestMove.setMove(state.getLastMove());
            System.out.println("depth: " + depth + ", value: " + bestMove.getScore() + ", move: " + bestMove.getMove());
            return bestMove;
        }

        List<Integer> actions = state.LegalMoves();

        Collections.shuffle(actions);   // TODO: Try to find the 'best' action instead of just shuffling.

        for(Integer action : actions) {
            reply = AlphaBeta(depth - 1, state.ResultingState(action), -beta, -alpha);
            reply.setScore(-reply.getScore());
            System.out.println("depth: " + depth + ", value: " + reply.getScore() + ", move: " + reply.getMove());
            //System.out.println(state.ResultingState(action));
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
