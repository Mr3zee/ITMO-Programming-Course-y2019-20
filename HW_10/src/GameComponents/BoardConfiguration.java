package GameComponents;

import static java.lang.Math.min;

public class BoardConfiguration {
    private final int width;
    private final int height;
    private final int length;

    public BoardConfiguration(final int height, final int width, final int length) {
        if (length > min(height, width)) {
            throw new IllegalArgumentException();
        }
        this.width = width;
        this.height = height;
        this.length = length;
    }


    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return "BoardConfiguration {" +
                "width = " + width +
                ", height = " + height +
                ", length = " + length +
                '}';
    }
}
