package GameComponents;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private Scanner scanner;
    private PrintStream out;

    public HumanPlayer(final PrintStream out, final Scanner scanner) {
        this.out = out;
        this.scanner = scanner;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(ClientBoard board, Cell cell) {
        int x, y;
        while (true) {
            try {
                x = scanner.nextInt();
                y = scanner.nextInt();
                final Move nextMove = new Move(x, y);
                if (board.isValid(nextMove, cell)) {
                    return nextMove;
                }
            } catch (InputMismatchException e) {
                return null;
            } catch (NoSuchElementException e) {
                scanner.nextLine();
                out.println("'x' coordinate should be a number between 0 and " + board.getBoardConfiguration().getWidth());
                out.println("'y' coordinate should be a number between 0 and " + board.getBoardConfiguration().getHeight());
            }
            out.print("Move is invalid.\nEnter your move again: ");
        }
    }
}
