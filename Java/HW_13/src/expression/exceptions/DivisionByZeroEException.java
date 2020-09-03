package expression.exceptions;

public class DivisionByZeroEException extends EvaluatingExpressionException {
    public DivisionByZeroEException(final int arg) {
        super("Division by zero - division " + arg + " by 0");
    }
}

