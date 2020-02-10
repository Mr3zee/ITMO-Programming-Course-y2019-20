package expression.exceptions.expExceptions;

public class UnderflowEEException extends EvaluatingExpressionException {
    public UnderflowEEException(final String operation, final int first, final int second) {
        super("Underflow - operation: \"" + operation + "\"; Arguments: (" + first + ", " + second + ")");
    }

    public UnderflowEEException(final String operation, final int first) {
        super("Underflow - operation: \"" + operation + "\"; Arguments: (" + first + ")");
    }
}
