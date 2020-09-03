package expression.exceptions;

public class EvaluatingExpressionException extends ExpressionException {
    public EvaluatingExpressionException(final String message) {
        super("EvaluatingExpressionException: " + message);
    }
}
