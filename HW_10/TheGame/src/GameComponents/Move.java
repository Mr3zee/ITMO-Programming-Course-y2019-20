package GameComponents;

public class Move {
    private final int x;
    private final int y;

    public Move(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Move {" +
                "x = " + x +
                ", y = " + y +
                '}';
    }
}
