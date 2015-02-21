
public class Node {
    private String move;
    private int score;

    public Node() {
        this.move = null;
        this.score = Integer.MIN_VALUE;
    }

    public void setScore(int score) { this.score = score; }

    public int getScore() { return this.score; }

    public void setMove(String move) { this.move = move; }

    public String getMove() { return this.move; }
}
