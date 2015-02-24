//package AdverserialSearchConnectFour;

/**
 * Created by ragnaradolf on 16/02/15.
 */
public class NewAgent implements Agent {

    private static int MAX_DEPTH = 20;

    private String role;
    public int playclock;
    private boolean myTurn;
    private State myState;

    public static int[][] evaluationTable = {{3, 4, 5, 7, 5, 4, 3},
                                             {4, 6, 8, 10, 8, 6, 4},
                                             {5, 8, 11, 13, 11, 8, 5},
                                             {5, 8, 11, 13, 11, 8, 5},
                                             {4, 6, 8, 10, 8, 6, 4},
                                             {3, 4, 5, 7, 5, 4, 3}};

    /*
	 *	init(String role, int playclock) is called once before you have to select the first action.
	 *	Use it to initialize the agent. role is either "WHITE" or "RED" and playclock is the number of
	 *	seconds after which nextAction must return.
	*/
    public void init(String role, int playclock) {
        this.role = role;
        System.out.println("role: " + role);
        this.playclock = playclock;
        myTurn = !role.equals("WHITE");

        // TODID: add your own initialization code here

        char[][] arr = new char[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        char[][] testarr = new char[][]{
                {'R', 'W', 'R', 'W', 'R', 'W', 'R'},
                {'W', 'R', 'W', 'R', 'W', 'R', 'W'},
                {'R', 'W', 'R', 'W', 'R', 'W', 'R'},
                {'R', 'W', 'R', 'W', 'R', 'W', 'R'},
                {'R', 'W', 'R', 'W', 'R', 'W', 'R'},
                {  0,   0,   0,   0,   0,   0,  0 }
        };

        myState = new State("RED", arr, 1, 1);

    }

    // lastDrop is 0 for the first call of nextAction (no action has been executed),
    // otherwise it is a number n with 0<n<8 indicating the column that the last piece was dropped in by the player whose turn it was
    public String nextAction(int lastDrop) {
        // TODO: 1. update your internal world model according to the action that was just executed

        if(lastDrop > 0) {
            myState = myState.ResultingState(lastDrop - 1);
        }

        myTurn = !myTurn;
        // TODO: 2. run alpha-beta search to determine the best move

        if (myTurn) {
            System.out.println("myState:");
            System.out.println(myState);
            Node nextMove = new Node();
            AlphaBetaSearch abs = new AlphaBetaSearch(playclock);
            try {
                for(int i = 1; i <= MAX_DEPTH; i++) {
                    System.out.println("i: " + i);
                    nextMove = abs.AlphaBeta(i, myState, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1);
                    System.out.println("nextMove.score: " + nextMove.getScore());
                    System.out.println("nextMove.move: " + nextMove.getMove());
                }
                System.out.println("nextMove.score: " + nextMove.getScore());
                System.out.println("nextMove.move: " + nextMove.getMove());
                return nextMove.getMove();
            } catch(OutOfTimeException ex) {
                System.out.println("nextMove.score: " + nextMove.getScore());
                System.out.println("nextMove.move: " + nextMove.getMove());
                return nextMove.getMove();
            }
        } else {
            return "NOOP";
        }
    }

    /*
     * Tester
     */
    public static void main(String[] args) {
        NewAgent agent = new NewAgent();
        char[][] arr = new char[6][7];
        State myState = new State(agent.role, arr);

        System.out.println("Printing state");
        System.out.println(myState);
    }
}
