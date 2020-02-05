package expression.exception;

public class BareSymbolExpressionException extends ExpressionException {
    public BareSymbolExpressionException(String expression) {
        super(expression);
    }
}
