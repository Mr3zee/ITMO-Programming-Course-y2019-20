package expression.parser;

public class NextWordParameters {
    private final String word;
    private final int position;
    private final String input;

    public NextWordParameters(String lexeme, int position, final String input) {
        this.word = lexeme;
        this.position = position;
        this.input = input;
    }

    public String getWord() {
        return word;
    }

    public int getPosition() {
        return position;
    }

    public String getInput() {
        return input;
    }
}