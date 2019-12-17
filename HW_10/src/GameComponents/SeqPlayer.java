package GameComponents;

public class SeqPlayer implements Player {

    @Override
    public Move move(ClientBoard board, Cell cell) {
        Move nextMove;
        for (int i = 0; i < board.getBoardConfiguration().getHeight(); i++) {
            for (int j = 0; j < board.getBoardConfiguration().getWidth(); j++) {
                nextMove = new Move(i, j);
                if (board.isValid(nextMove, cell)) {
                    return nextMove;
                }
            }
        }
        throw new AssertionError("No moves are available");
    }
}