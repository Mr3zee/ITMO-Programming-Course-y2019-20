package expression.exceptions;

public class DivisionByZeroEException extends EvaluatingExpressionException {
    public DivisionByZeroEException(final String arg) {
        super("Division by zero - division " + arg + " by 0");
    }
}

