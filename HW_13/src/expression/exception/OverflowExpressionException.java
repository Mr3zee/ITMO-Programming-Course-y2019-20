package expression.exception;

public class OverflowExpressionException extends ExpressionException {
    public OverflowExpressionException(String expression) {
        super(expression);
    }

    @Override
    public String getMessage() {
        return "OverflowException in " + getMessage() + "\n";
    }
}
