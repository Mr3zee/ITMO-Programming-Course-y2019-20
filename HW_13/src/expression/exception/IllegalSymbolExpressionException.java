package expression.exception;

public class IllegalSymbolExpressionException extends ExpressionException {
    public IllegalSymbolExpressionException(String expression) {
        super(expression);
    }
}
