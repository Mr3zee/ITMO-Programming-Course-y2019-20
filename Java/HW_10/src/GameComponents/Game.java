package GameComponents;

import java.io.PrintStream;
import java.util.Random;

public class Game {
    private final Player player1;
    private final Player player2;
    private boolean player1GoFirst;
    private final boolean showLog;
    private PrintStream out;

    public Game(final Player player1, final Player player2, final boolean showLog, PrintStream out) {
        this.player1 = player1;
        this.player2 = player2;
        this.showLog = showLog;
        this.out = out;
    }

    public Game(final Player player1, final Player player2, final boolean showLog) {
       this(player1, player2, showLog, System.out);
    }

    public int playTheGame(GameBoard board) {
        showLog("Board:\n" + board + "\n\n");
//        setUpAI(player1, player2);
        whoGoesFirst();
        final int playerFirstNumber = player1GoFirst ? 1 : 2;
        final int playerSecondNumber = 3 - playerFirstNumber;
        while (true) {
            final Result firstPlayerResult = playerTurn(board, player1GoFirst ? player1 : player2, playerFirstNumber);
            if (firstPlayerResult != Result.UNDEF) {
                return result(firstPlayerResult, playerFirstNumber, playerSecondNumber);
            }
            final Result secondPlayerResult = playerTurn(board, player1GoFirst ? player2 : player1, playerSecondNumber);
            if (secondPlayerResult != Result.UNDEF) {
                return result(secondPlayerResult, playerSecondNumber, playerFirstNumber);
            }
        }
    }

    private int result(final Result playerResult, final int i, final int j) {
        return playerResult == Result.DRAW ? 0 : playerResult == Result.LOSE ? j : i;
    }

    private void whoGoesFirst() {
        Random random = new Random();
        player1GoFirst = random.nextInt(2) == 0;
    }

//    private void setUpAI (Player player1, Player player2) {
//        if (player1 instanceof AIPlayer) {
//            ((AIPlayer) player1).startGame();
//        }
//        if (player2 instanceof AIPlayer) {
//            ((AIPlayer) player2).startGame();
//        }
//    }

    private Result playerTurn(GameBoard board, Player player, int playerNumber) {
        showLog(board.getTurn() + "'s turn (Player " + playerNumber + ")\nEnter coordinates in format [x, space, y]: ");
        Move move = player.move(board.getClientBoard(), board.getTurn());
        if (move != null) {
            showLog(move.getX() + " " + move.getY());
        }
        Result result = board.turnResult(move);
        showLog("\nBoard:\n" + board + "\n\n");
        if (showLog) {
            if (result == Result.WIN) {
                out.println("\nPlayer " + playerNumber + " won!\n");
            } else if (result == Result.LOSE) {
                out.println("\nPlayer " + (3 - playerNumber) + " won!\n");
            } else if (result == Result.DRAW) {
                out.println("\nDraw!\n");
            }
        }
        return result;
    }

    private void showLog(final String string) {
        if (showLog) {
            out.print(string);
        }
    }
}
