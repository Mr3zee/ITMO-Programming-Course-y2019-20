package expression.exceptions;

public class OverflowEEException extends EvaluatingExpressionException {
    public OverflowEEException(final String operation, final int arg) {
        super("Overflow - operation: \"" + operation + "\"; Argument: (" + arg + ")");
    }

    public OverflowEEException(final String operation, final int first, final int second) {
        super("Overflow - operation: \"" + operation + "\"; Arguments: (" + first + ", " + second + ")");
    }
}