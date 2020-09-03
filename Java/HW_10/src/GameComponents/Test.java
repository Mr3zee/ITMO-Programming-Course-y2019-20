package GameComponents;

import java.util.Arrays;

public class Test {

    private static final BoardConfiguration boardConfiguration = new BoardConfiguration(4, 4, 3);
    private static Cell[][] cells = new Cell[boardConfiguration.getHeight()][boardConfiguration.getWidth()];

    public static void main(String[] args) {
        cells[0][0] = Cell.X;
        cells[0][1] = Cell.O;
        cells[0][2] = Cell.X;
        cells[0][3] = Cell.E;
        cells[1][0] = Cell.X;
        cells[1][1] = Cell.O;
        cells[1][2] = Cell.O;
        cells[1][3] = Cell.E;
        cells[2][0] = Cell.O;
        cells[2][1] = Cell.X;
        cells[2][2] = Cell.E;
        cells[2][3] = Cell.E;
        cells[3][0] = Cell.X;
        cells[3][1] = Cell.E;
        cells[3][2] = Cell.O;
        cells[3][3] = Cell.E;
//        Checker checker = new Checker(cells, boardConfiguration);
//        System.out.println(checker.check(1, 1, 2));
        print();
        AIPlayer aiPlayer = new AIPlayer(cells, boardConfiguration, 7);
        Move move = aiPlayer.move(new Move(1, 2), Cell.X);
        System.out.println(move);
    }

    private static void print() {
        for (int i = 0; i < boardConfiguration.getHeight(); i++) {
            for (int j = 0; j < boardConfiguration.getWidth(); j++) {
                System.out.print(cells[i][j] + " ");
            }
            System.out.println();
        }
    }
}
