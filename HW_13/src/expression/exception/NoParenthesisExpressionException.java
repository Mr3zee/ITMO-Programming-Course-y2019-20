package expression.exception;

public class NoParenthesisExpressionException extends ExpressionException {
    public NoParenthesisExpressionException(String expression) {
        super(expression);
    }
}
