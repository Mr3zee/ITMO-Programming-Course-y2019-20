package GameComponents;

import java.util.Map;

public class MNKBoard implements GameBoard {
    private final BoardConfiguration boardConfiguration;
    private Cell currentCell;
    private final Cell[][] cells;
    private final Map<Cell, Character> BOARDSYMBOLS = Map.of(Cell.X, 'X', Cell.O, 'O', Cell.E, '.');
    private int empty;
    private final Checker checker;
    private Move lastMove;

    public MNKBoard(final BoardConfiguration boardConfiguration) {
        this.boardConfiguration = boardConfiguration;
        cells = new Cell[boardConfiguration.getHeight()][boardConfiguration.getWidth()];
        for (int i = 0; i < boardConfiguration.getHeight(); i++) {
            for (int j = 0; j < boardConfiguration.getWidth(); j++) {
                cells[i][j] = Cell.E;
            }
        }
        checker = new Checker(cells, boardConfiguration);
        empty = boardConfiguration.getHeight() * boardConfiguration.getWidth();
        currentCell = Cell.X;
    }

    @Override
    public boolean isValid(Move move, Cell cell) {
        return move != null &&
                0 <= move.getX() && move.getX() < boardConfiguration.getHeight() &&
                move.getY() < boardConfiguration.getWidth() && move.getY() > -1 &&
                cells[move.getX()][move.getY()] == Cell.E && cell == currentCell;
    }

    @Override
    public Cell getTurn() {
        return currentCell;
    }

    @Override
    public BoardConfiguration getBoardConfiguration() {
        return boardConfiguration;
    }

    @Override
    public Result turnResult(final Move move) {
        if (!isValid(move, currentCell)) {
            return Result.LOSE;
        }
        lastMove = move;
        cells[move.getX()][move.getY()] = currentCell;
        final Result result = checker.check(move, --empty);
        if (result != Result.UNDEF) {
            return result;
        }
        currentCell = currentCell == Cell.X ? Cell.O : Cell.X;
        return Result.UNDEF;
    }

    @Override
    public ClientBoard getClientBoard() {
        return this;
    }

    public Move getLastMove() {
        return lastMove;
    }

    @Override
    public GameBoard newBoard() {
        return new MNKBoard(boardConfiguration);
    }

    @Override
    public String toString() {
        StringBuilder board = new StringBuilder("  ");
        for (int i = 0; i < boardConfiguration.getWidth(); i++) {
            board.append(i).append(' ');
        }
        board.append(" <- y");
        for (int u = 0; u < boardConfiguration.getHeight(); u++) {
            board.append('\n').append(u).append(" ");
            for (int v = 0; v < boardConfiguration.getWidth(); v++) {
                board.append(BOARDSYMBOLS.get(cells[u][v])).append(' ');
            }
        }
        board.append("\n \n^\n|\nx");
        return board.toString();
    }
}
