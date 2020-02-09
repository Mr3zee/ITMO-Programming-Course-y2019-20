package expression.exceptions.expExceptions;

public class ParsingExpressionException extends ExpressionException {
    public ParsingExpressionException(final String message, final int position, final String input) {
        super("ParsingExpressionException: " + message + " in position " + position + " || \"" + input + "\"");
    }
}
