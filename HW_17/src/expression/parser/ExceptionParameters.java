package expression.parser;

public class ExceptionParameters {
    private final String word;
    private final int position;
    private final String input;

    public ExceptionParameters(String lexeme, int position, final String input) {
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