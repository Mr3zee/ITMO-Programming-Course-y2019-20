package GameComponents;

import java.util.*;

public class RandomPlayer implements Player {
    private Random random;

    public RandomPlayer() {
        random = new Random();
    }

    @Override
    public Move move(ClientBoard board, Cell cell) {
        Move nextMove;
        do {
            nextMove = new Move(random.nextInt(board.getBoardConfiguration().getHeight()), random.nextInt(board.getBoardConfiguration().getWidth()));
        } while (!board.isValid(nextMove, cell));
        return nextMove;
    }
}
