package expression.exceptions;

public class ParsingExpressionException extends ExpressionException {
    private final int position;
    private final String expression;
    public ParsingExpressionException(final String message, final int position, final String input) {
        super("ParsingExpressionException: " + message + " in position " + position + " || \"" + input + "\"");
        this.expression = input;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public String getExpression() {
        return expression;
    }
}
