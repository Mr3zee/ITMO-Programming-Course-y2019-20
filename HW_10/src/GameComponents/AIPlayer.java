package GameComponents;

public class AIPlayer implements Player {
    private Checker checker;
    private Cell[][] cells;
    private BoardConfiguration boardConfiguration;
    private Cell playerOpponent;
    private Cell playerAI;
    private int empty;
    private int emptyBegin;
    private boolean firstMove;
    private int level;
    private final Move INVALID = new Move(-1, -1);

    public AIPlayer(Cell[][] cells, BoardConfiguration boardConfiguration, int empty) {
        level = 0;
        this.cells = cells;
        this.boardConfiguration = boardConfiguration;
        checker = new Checker(cells, boardConfiguration);
        this.empty = empty;
    }

    public AIPlayer() {
    }

    private void setUp(ClientBoard board) {
        boardConfiguration = board.getBoardConfiguration();
        cells = new Cell[boardConfiguration.getHeight()][boardConfiguration.getWidth()];
        for (int i = 0; i < boardConfiguration.getHeight(); i++) {
            for (int j = 0; j < boardConfiguration.getWidth(); j++) {
                cells[i][j] = Cell.E;
            }
        }
        checker = new Checker(cells, boardConfiguration);
        empty = boardConfiguration.getHeight() * boardConfiguration.getWidth();
    }

    void startGame() {
        firstMove = true;
    }

    @Override
    public Move move(ClientBoard board, Cell cell) {
        if (firstMove) {
            setUp(board);
            firstMove = false;
        }
        if (board.getLastMove() == null) {
            Move playerAIMove = new RandomPlayer().move(board, cell);
            cells[playerAIMove.getX()][playerAIMove.getY()] = cell;
            empty--;
            return playerAIMove;
        }
        return move(board.getLastMove(), cell);
    }

    public Move move(Move lastMove, Cell cell) {
        level = 0;
        emptyBegin = empty;
        playerAI = cell;
        playerOpponent = cell == Cell.X ? Cell.O : Cell.X;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        cells[lastMove.getX()][lastMove.getY()] = playerOpponent;
        Move playerAIMove = minimax(false, lastMove, empty - 1, alpha, beta).getMove();
        cells[playerAIMove.getX()][playerAIMove.getY()] = playerAI;
        empty -= 2;
        return playerAIMove;
    }

    private MoveScore minimax(boolean playerIsAI, Move lastMove, int empty, int alpha, int beta) {
        Result result = checker.check(lastMove, empty);
//        System.out.println(lastMove + " " + result + " " + playerIsAI + " " + empty + " level " + level++);
        if (result != Result.UNDEF) {
            if (result != Result.DRAW) {
                if (playerIsAI) {
                    return new MoveScore(lastMove, empty);
                } else {
                    return new MoveScore(lastMove, empty - emptyBegin);
                }
            }
            return new MoveScore(lastMove, 0);
        }
        boolean nextPlayer = !playerIsAI;
        MoveScore bestMove = new MoveScore(INVALID, nextPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE);
        for (int i = 0; i < boardConfiguration.getHeight(); i++) {
            for (int j = 0; j < boardConfiguration.getWidth(); j++) {
                if (cells[i][j] == Cell.E) {
                    Move availableMove = new Move(i, j);
                    MoveScore move = new MoveScore(availableMove);
                    cells[i][j] = nextPlayer ? playerAI : playerOpponent;
                    move.setScore(minimax(nextPlayer, availableMove, empty - 1, alpha, beta).getScore());
                    cells[i][j] = Cell.E;
                    if (nextPlayer) {
                        bestMove = max(bestMove, move);
                        alpha = Math.max(alpha, move.getScore());
                    } else {
                        bestMove = min(bestMove, move);
                        beta = Math.min(beta, move.getScore());
                    }
                    if (beta < alpha) {
                        break;
                    }
                }
            }
        }
        return bestMove;
    }

    private MoveScore max(MoveScore bestMove, MoveScore move) {
        if (bestMove.getScore() > move.getScore()) {
            return bestMove;
        }
        return move;
    }

    private MoveScore min(MoveScore bestMove, MoveScore move) {
        if (bestMove.getScore() < move.getScore()) {
            return bestMove;
        }
        return move;
    }
}