//package AdverserialSearchConnectFour;

import java.util.*;

public class State {

    private char[][] grid;
    private String player;
    private int lastCol;
    private int lastRow;

    public State(String player, char[][] grid) {
        this.grid = grid;
        this.player = player;
        this.lastRow = -1;
        this.lastCol = -1;
    }
    
    public State(String player, char[][] grid, int row, int col) {
        this.grid = grid;
        this.player = player;
        this.lastRow = row;
        this.lastCol = col;
    }

    public State(State that) {
        this.grid = deepCopy(that.grid);
        this.player = that.player;
        this.lastCol = that.lastCol;
        this.lastRow = that.lastRow;
    }

    public String getLastMove() {
        //return (this.lastCol + 1);
        return "(DROP " + (this.lastCol + 1) + ")";
    }

    public List<Integer> LegalMoves() {
        List<Integer> moves = new ArrayList<Integer>();

        for(int i = 0; i < 7; i++) {
            if(this.grid[5][i] == 0) {
                moves.add(i);
            }
        }

        return moves;
    }

    public boolean TerminalTest() {
        boolean isTerminal = true;
        for(int i = 0; i < 7; i++) {
            if(this.grid[5][i] == 0) {
                isTerminal = false;
                break;
            }
        }
        return (isTerminal || GoalTest());
    }

    public State ResultingState(Integer col) {
        char[][] newGrid = deepCopy(this.grid);

        char token;

        if(this.player.charAt(0) == 'W') {
            token = 'R';
        } else {
            token = 'W';
        }

        int row = 0;
        for(int i = 0; i < 6; i++) {
            if(this.grid[i][col] != 0) {
                row++;
            } else {
                break;
            }
        }

        newGrid[row][col] = token;

        String newPlayer;
        if(token == 'W') {
            newPlayer = "WHITE";
        } else {
            newPlayer = "RED";
        }

        return new State(newPlayer, newGrid, row, col);
    }


    private static char[][] deepCopy(char[][] original) {
        if(original == null) {
            return null;
        }

        final char[][] result = new char[original.length][];
        for(int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }

        return result;
    }

    public boolean GoalTest() {
        char token = this.player.charAt(0);

        /**
         * Vertical check
         */
        
        int count = 0;
        if(this.lastRow >= 4) {
            for(int i = lastRow; i > lastRow - 4; i--) {
                if(this.grid[i][lastCol] != token) {
                    break;
                } else {
                    count++;
                }
            }
            if(count == 4) {
                return true;
            }
        }

        /**
         * Horizontal check
         */
        boolean isOver = true;
        if(this.lastCol >= 3) {
            for(int i = lastCol - 3; i < 4; i++) {
                for (int j = i; j < i + 4; j++) {
                    if(this.grid[lastRow][j] != token) {
                        isOver = false;
                        break;
                    }
                }
                if(isOver) {
                    return true;
                }
                isOver = true;
            }
        } else {
            for(int i = 0; i <= lastCol; i++) {
                for(int j = i; j < i + 4; j++) {
                    if(this.grid[lastRow][j] != token) {
                        isOver = false;
                        break;
                    }
                }
                if(isOver) {
                    return true;
                }
                isOver = true;
            }
        }

        /**
         * Diagonal check
         */

        int startRow = 0, startCol = 0;
        int i, j;
        isOver = true;
        
        if(this.lastCol >= this.lastRow){
        	if(this.lastRow < 3){
        		startRow = 0;
        		startCol = this.lastCol - this.lastRow;
        	}
        	else{
        		startRow = this.lastRow - 3;
        		startCol = this.lastCol - 3;
        	}
        }
        else{
        	if(this.lastCol < 3){
        		startCol = 0;
        		startRow = this.lastRow - this.lastCol;
        	}
        	else{
        		startRow = this.lastRow - 3;
        		startCol = this.lastCol - 3;
        	}
        }
        
        /*for (i = this.lastRow, j = this.lastCol; i >= 0 && j >= 0; i--, j--) {
            for (j = this.lastCol; j >= 0; j--) {
                if (i == 0 || j == 0 || i == (this.lastRow - 3) || j == (this.lastCol - 3)) {
                    startRow = i;
                    startCol = j;
                    break;
                }
            }
            if (startRow == i && startCol == j) {
                break;
            }
        }*/

        for ( ; startRow < 3 && startCol < 4 ; startRow++, startCol++) {
            for (i = startRow, j = startCol; i < startRow + 3 && j < startCol + 3; i++, j++) {
                //for (j = startCol; j < 4; j++) {
                if (this.grid[i][j] != token) {
                    isOver = false;
                    break;
                }
            }
            if (isOver) {
                return true;
            }
            isOver = true;
        }

        /*for (i = this.lastRow; i >=5; i++) {
            for (j = this.lastCol; j >= 0; j--) {
                if (i == 5 || j == 0 || i == (this.lastRow + 3) || j == (this.lastCol - 3)) {
                    startRow = i;
                    startCol = j;
                    break;
                }
            }
            if (startRow == i && startCol == j) {
                break;
            }
        }*/
        
        if(this.lastCol >= (5 - this.lastRow)){
        	if(this.lastRow > 2){
        		startRow = 5;
        		startCol = this.lastCol - (5 - this.lastRow);
        	}
        	else{
        		startRow = this.lastRow + 3;
        		startCol = this.lastCol - 3;
        	}
        }
        else{
        	if(this.lastCol < 3){
        		startRow = this.lastRow + this.lastCol;
        		startCol = 0;
        	}
        	else{
        		startRow = this.lastRow + 3;
        		startCol = this.lastCol - 3;
        	}
        }
        
        for ( ; startRow > 2 && startCol < 4 ; startRow--, startCol++) {
            for (i = startRow, j = startCol; i > startRow - 3 && j < startCol + 3; i--, j++) {
                //for (j = startCol; j < 4; j++) {
                if (this.grid[i][j] != token) {
                    isOver = false;
                    break;
                }
            }
            if (isOver) {
                return true;
            }
            isOver = true;
        }

        /*for ( ; startRow < 3 || startCol > 3 ; startRow--, startCol++) {
            for (i = startRow; i > 2; i--) {
                for (j = startCol; j < 4; j++) {
                    if (i < 1 || j > 6) {
                        isOver = false;
                        break;
                    }
                    if(this.grid[i][j] != token) {
                        isOver = false;
                        break;
                    }
                }
                if (isOver) {
                    return true;
                }
                isOver = true;
            }
        }*/

        return false;
    }

    public int eval() {
        int utility = 138;
        int sum = 0;

        if(this.GoalTest()) {
            return -1000;
        }

        char token;

        if(this.player.charAt(0) == 'W') {
            token = 'R';
        } else {
            token = 'W';
        }

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 7; j++) {
                if(this.grid[i][j] == token) {
                    sum += NewAgent.evaluationTable[i][j];
                } else if(this.grid[i][j] != 0) {
                    sum -= NewAgent.evaluationTable[i][j];
                }
            }
        }

        /*
        int redCount = 0;
        int whiteCount = 0;
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                if((this.grid[i][j] == this.grid[i][j + 1]) && (this.grid[i][j] != 0)) {
                    if(this.grid[i][j] == 'W') {
                        whiteCount++;
                    } else {
                        redCount++;
                    }
                }
            }
        }

        if(whiteCount > 0) {
            whiteCount++;
        }
        if(redCount > 0) {
            redCount++;
        }

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 6; j++) {
                if((this.grid[j][i]) == this.grid[j][i + 1] && (this.grid[j][i] != 0)) {
                    if(this.grid[j][i] == 'W') {
                        whiteCount++;
                    } else {
                        redCount++;
                    }
                }
            }
        }

        return whiteCount - redCount;
        */

        
        
        int redCount = 0, whiteCount = 0;
        
        for(int k = 0; k < 7; k++){
        	for(int l = 0; l < 6; l++){
        		
        		/**
                 * Vertical check
                 */
        		
        		if(l >= 4) {
                    for(int i = l; i > l - 4; i--) {
                        if(this.grid[i][k] == 'R') {
                            redCount++;
                        } else if(this.grid[i][k] == 'W'){
                            whiteCount++;
                        }
                    }
                    if(whiteCount == 3 && redCount == 0) {
                    	if(token == 'R'){
                    		sum -= 20;
                    	}
                    	else{
                    		sum += 20;
                    	}
                    }
                    else if(whiteCount == 0 && redCount == 3){
                    	if(token == 'R'){
                    		sum += 20;
                    	}
                    	else{
                    		sum -= 20;
                    	}
                    }
                }
        		
        		whiteCount = 0;
        		redCount = 0;

                /**
                 * Horizontal check
                 */
                if(k == 4) {
                    for(int i = k - 3; i < 4; i++) {
                    	whiteCount = 0;
                		redCount = 0;
                        for (int j = i; j < i + 4; j++) {
                        	if(this.grid[l][j] == 'R') {
                                redCount++;
                            } else if(this.grid[l][j] == 'W'){
                                whiteCount++;
                            }
                        }
                        if(whiteCount == 3 && redCount == 0) {
                        	if(token == 'R'){
                        		sum -= 20;
                        	}
                        	else{
                        		sum += 20;
                        	}
                        }
                        else if(whiteCount == 0 && redCount == 3){
                        	if(token == 'R'){
                        		sum += 20;
                        	}
                        	else{
                        		sum -= 20;
                        	}
                        }
                    }
                }
                
                whiteCount = 0;
                redCount = 0;

                /**
                 * Diagonal check
                 */
                
                if(k == 4){
                	
                	int startRow = 0, startCol = 0;
                    int i, j;
                    
                    if(k >= l){
                    	if(l < 3){
                    		startRow = 0;
                    		startCol = k - l;
                    	}
                    	else{
                    		startRow = l - 3;
                    		startCol = k - 3;
                    	}
                    }
                    else{
                		startRow = l - 3;
                		startCol = k - 3;
                    }

                    for ( ; startRow < 3 && startCol < 4 ; startRow++, startCol++) {
                        whiteCount = 0;
                        redCount = 0;
                    	for (i = startRow, j = startCol; i < startRow + 3 && j < startCol + 3; i++, j++) {
                        	if(this.grid[i][j] == 'R') {
                                redCount++;
                            } else if(this.grid[i][j] == 'W'){
                                whiteCount++;
                            }
                        }
                        if(whiteCount == 3 && redCount == 0) {
                        	if(token == 'R'){
                        		sum -= 20;
                        	}
                        	else{
                        		sum += 20;
                        	}
                        }
                        else if(whiteCount == 0 && redCount == 3){
                        	if(token == 'R'){
                        		sum += 20;
                        	}
                        	else{
                        		sum -= 20;
                        	}
                        }
                    }
                    
                    if(k >= (5 - l)){
                    	if(l > 2){
                    		startRow = 5;
                    		startCol = k - (5 - l);
                    	}
                    	else{
                    		startRow = l + 3;
                    		startCol = k - 3;
                    	}
                    }
                    else{
                    	if(k < 3){
                    		startRow = l + k;
                    		startCol = 0;
                    	}
                    	else{
                    		startRow = l + 3;
                    		startCol = k - 3;
                    	}
                    }
                    
                    for ( ; startRow > 2 && startCol < 4 ; startRow--, startCol++) {
                        whiteCount = 0;
                        redCount = 0;
                    	for (i = startRow, j = startCol; i > startRow - 3 && j < startCol + 3; i--, j++) {
                        	if(this.grid[i][j] == 'R') {
                                redCount++;
                            } else if(this.grid[i][j] == 'W'){
                                whiteCount++;
                            }
                        }
                        if(whiteCount == 3 && redCount == 0) {
                        	if(token == 'R'){
                        		sum -= 20;
                        	}
                        	else{
                        		sum += 20;
                        	}
                        }
                        else if(whiteCount == 0 && redCount == 3){
                        	if(token == 'R'){
                        		sum += 20;
                        	}
                        	else{
                        		sum -= 20;
                        	}
                        }
                    }
                }
        	}
        }
        return utility + sum;
    }


    @Override
    public String toString() {
        String output = " ";
        for(int i = 0; i < this.grid[0].length; i++) {
            output += "__";
        }

        output += "\n";

        for(char[] row : grid) {
            output += "| ";
            for(char token : row) {
                if(token == 0) {
                    output += "  ";
                } else {
                    output += token + " ";
                }
            }
            output += '|';
            output += "\n";
        }

        output += " ";

        for(int i = 0; i < this.grid[0].length; i++) {
            output += "--";
        }

        return output;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof State)) {
            return false;
        }
        State that = (State) obj;
        for(int i = 0; i < this.grid.length; i++) {
            for(int j = 0; j < this.grid[i].length; j++) {
                if(this.grid[i][j] != that.grid[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        char check;
        if(this.player.equals("WHITE")) {
            check = 'w';
        } else {
            check = 'r';
        }
        for(char[] row : grid) {
            for(char token : row) {
                if(token == check) {
                    hash++;
                }
            }
        }
        return hash * 6151;
    }

    public void DoMove(int action) {
        char token;

        if(this.player.charAt(0) == 'W') {
            token = 'R';
        } else {
            token = 'W';
        }

        int row = 0;
        for(int i = 0; i < 6; i++) {
            if(this.grid[i][action] != 0) {
                row++;
            } else {
                break;
            }
        }

        this.grid[row][action] = token;

        if(token == 'W') {
            this.player = "WHITE";
        } else {
            this.player = "RED";
        }
    }

    public void UndoMove(int action) {
        int row = 0;
        for(int i = 0; i < 6; i++) {
            if(this.grid[i][action] != 0) {
                row++;
            } else {
                break;
            }
        }

        this.grid[--row][action] = 0;

        if(this.player.equals("WHITE")) {
            this.player = "RED";
        } else {
            this.player = "WHITE";
        }
    }

    public static void main(String[] args) {
        char[][] arr = new char[][]{
            {'\u0000', 'R', 'W',      'R',      'W',      'R',      'W'     },
            {'\u0000', 'R', 'R',      'R',      'W',      'W',      'R'     },
            {'\u0000', 'W', 'R',      'R',      'W',      'R',      'W'     },
            {0       , 'W', '\u0000', 'R',      'W',      'W',      '\u0000'},
            {'\u0000', 'W', '\u0000', 'W',      'W',      'R',      0       },
            {'\u0000', 'R', '\u0000', '\u0000', '\u0000', 0,        '\u0000'}
        };

        String player = "WHITE";

        State testState = new State(player, arr, 2, 6);

        System.out.println(testState);
        Collection<Integer> moves = testState.LegalMoves();

        System.out.println(testState.eval());
        /*
        testState.resultingState("(DROP 1)");
        testState.resultingState("(DROP 2)");
        testState.resultingState("(DROP 3)");
        testState.resultingState("(DROP 4)");
        testState.resultingState("(DROP 5)");
        testState.resultingState("(DROP 6)");
        testState.resultingState("(DROP 7)");
        */
        //System.out.println(testState);
        //System.out.println(testState.goalTest());
    }
}
