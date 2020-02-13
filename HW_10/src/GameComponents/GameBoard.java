package GameComponents;

public interface GameBoard extends ClientBoard {
    @Override
    boolean isValid(Move move, Cell cell);

    @Override
    Move getLastMove();

    @Override
    BoardConfiguration getBoardConfiguration();

    Cell getTurn();

    Result turnResult(Move move);

    ClientBoard getClientBoard();

    GameBoard newBoard();
}
