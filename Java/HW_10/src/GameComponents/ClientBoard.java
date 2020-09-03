package GameComponents;

public interface ClientBoard {
    boolean isValid(Move move, Cell cell);
    Move getLastMove();
    BoardConfiguration getBoardConfiguration();
}
