//package AdverserialSearchConnectFour;

/**
 * Created by ragnaradolf on 16/02/15.
 */
public class NewAgent implements Agent {

    private String role;
    private int playclock;
    private boolean myTurn;

    /*
	 *	init(String role, int playclock) is called once before you have to select the first action.
	 *	Use it to initialize the agent. role is either "WHITE" or "RED" and playclock is the number of
	 *	seconds after which nextAction must return.
	*/
    public void init(String role, int playclock) {
        this.role = role;
        this.playclock = playclock;
        myTurn = role.equals("WHITE");

        // TODO: add your own initialization code here

    }

    // lastDrop is 0 for the first call of nextAction (no action has been executed),
    // otherwise it is a number n with 0<n<8 indicating the column that the last piece was dropped in by the player whose turn it was
    public String nextAction(int lastDrop) {
        // TODO: 1. update your internal world model according to the action that was just executed

        myTurn = !myTurn;
        // TODO: 2. run alpha-beta search to determine the best move

        if (myTurn) {
            return "(DROP 1)";
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
