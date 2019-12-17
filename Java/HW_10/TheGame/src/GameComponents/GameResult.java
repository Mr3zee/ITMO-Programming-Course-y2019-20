package GameComponents;

public class GameResult {
    private final int player1;
    private final int player2;
    private final int result;

    public GameResult(int player1, int player2, int result) {
        this.player1 = player1;
        this.player2 = player2;
        this.result = result;
    }

    public int getPlayer1() {
        return player1;
    }

    public int getPlayer2() {
        return player2;
    }

    public int getResult() {
        return result;
    }
}
