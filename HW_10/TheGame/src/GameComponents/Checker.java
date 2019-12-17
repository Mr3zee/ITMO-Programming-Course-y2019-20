package GameComponents;

public class Checker {
    private Cell[][] cells;
    private final BoardConfiguration boardConfiguration;
    private int x;
    private int y;
    private Cell currentCell;

    public Checker(final Cell[][] cells, final BoardConfiguration boardConfiguration) {
        this.cells = cells;
        this.boardConfiguration = boardConfiguration;
    }

    public Checker(final BoardConfiguration boardConfiguration) {
        this.boardConfiguration = boardConfiguration;
    }

    public Result check(Move move, int empty) {
        this.x = move.getX();
        this.y = move.getY();
        currentCell = cells[x][y];
        if (checkInLine(1, 0) || checkInLine(0, 1) ||
                checkInLine(1, 1) || checkInLine(-1, 1)) {
            return Result.WIN;
        }
        if (empty == 0) {
            return Result.DRAW;
        }
        return Result.UNDEF;
    }

    private boolean checkInLine(int iChange, int jChange) {
        int count = 0;
        count += whileInBoardAndEqual(iChange, jChange, 1) + whileInBoardAndEqual(iChange, jChange, -1) - 1;
        return (count >= boardConfiguration.getLength());
    }

    private int whileInBoardAndEqual(int iChange, int jChange, int factor) {
        int count = 0;
        int i = x;
        int j = y;
        while (inside(i, j) && cells[i][j] == currentCell) {
            i += iChange * factor;
            j += jChange * factor;
            count++;
        }
        return count;
    }

    private boolean inside(int i, int j) {
        return i >= 0 && j >= 0 && i < boardConfiguration.getWidth() && j < boardConfiguration.getHeight();
    }
}
