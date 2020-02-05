package expression.exception;

public class ExpressionException extends RuntimeException {

    public ExpressionException(String expression) {
        super(expression);
    }

    @Override
    public String getMessage() {
        return "ExpressionException in " + getMessage() + "\n";
    }
}
