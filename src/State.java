import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class State {

    private char[][] grid;
    private String player;
    private int lastCol;
    private int lastRow;

    public State(String player, char[][] grid, int col, int row) {
        this.grid = grid;
        this.player = player;
        this.lastCol = col;
        this.lastRow = row;
    }

    public Collection<String> LegalMoves() {
        /*
         * 2d array
         */
        List<String> moves = new ArrayList<String>();

        for(int i = 0; i < this.grid.length; i++) {
            if(this.grid[i][6] != 0) {
                moves.add("(DROP " + i + 1 + ")");
            }
        }

        return moves;
    }

    public State resultingState(String action) {
        char[][] newGrid = this.grid.clone();

        char token = this.player.charAt(0);

        int col = 0;
        Matcher m = Pattern.compile("\\(\\s*DROP\\s+([0-9]+)\\s*\\)").matcher(action);
        if(m.matches()) {
            col = (Integer.parseInt(m.group(1))) - 1;
        } else {
            System.out.println("Error. Not a valid column number. Assemble the troops.");
            System.exit(1);
        }

        int row = 0;
        for(int i = 0; i < this.grid[col].length; i++) {
            if(this.grid[col][i] != 0) {
                row++;
            } else {
                break;
            }
        }

        newGrid[row][col] = token;

        String newPlayer;
        if(this.player.equals("WHITE")) {
            newPlayer = "RED";
        } else {
            newPlayer = this.player;
        }

        return new State(newPlayer, newGrid, col, row);
    }

    public boolean goalTest() {
        char token = this.player.charAt(0);

        /**
         * Vertical check
         */
        int count = 0;
        if(this.lastRow >= 4) {
            for(int i = lastRow; i > lastRow - 4; i--) {
                if(this.grid[lastCol][i] != token) {
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
                    if(this.grid[i][j] != token) {
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
            for(int i = 0; i < lastCol; i++) {
                for(int j = i; j < i + 4; j++) {
                    if(this.grid[i][j] != token) {
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

        if(this.lastCol >= 3) {
            
        }

        return false;
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
}
