package expression.exception;

public class DivisionByZeroException extends ExpressionException {
    public DivisionByZeroException(String expression) {
        super(expression);
    }

    @Override
    public String getMessage() {
        return "DivisionByZeroException in " + getMessage() + "\n";
    }
}

