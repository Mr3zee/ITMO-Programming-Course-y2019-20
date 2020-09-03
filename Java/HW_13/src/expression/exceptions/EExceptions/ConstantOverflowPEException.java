package expression.exceptions.EExceptions;

public class ConstantOverflowPEException extends ParsingExpressionException {
    public ConstantOverflowPEException(final String number, final int position, final String input) {
        super("Overflow of the constant - \"" + number + "\"", position, input);
    }
}
