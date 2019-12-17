package GameComponents;

public class MoveScore {
    private Move move;
    private int score;

    public MoveScore(Move move) {
        this.move = move;
        score = -2;
    }

    public MoveScore(Move move, int score) {
        this.move = move;
        this.score = score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Move getMove() {
        return move;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "MoveScore { " +
                "move = " + move +
                ", score = " + score +
                " }";
    }
}
