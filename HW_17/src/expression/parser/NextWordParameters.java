package expression.parser;

public class NextWordParameters {
    private final String lexeme;
    private final int position;
    private final String input;

    public NextWordParameters(String lexeme, int position, final String input) {
        this.lexeme = lexeme;
        this.position = position;
        this.input = input;
    }

    public String getWord() {
        return lexeme;
    }

    public int getPosition() {
        return position;
    }

    public String getInput() {
        return input;
    }
}