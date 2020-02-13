package expression.exceptions.EExceptions;

public class ConstantUnderflowPEException extends ParsingExpressionException {
    public ConstantUnderflowPEException(final String number, final int position, final String input) {
        super("Underflow of the constant - \"" + number + "\"", position, input);
    }
}
