package expression.exceptions;

import expression.parser.NextWordParameters;

public class ConstantOverflowPEException extends ParsingExpressionException {
    public ConstantOverflowPEException(final NextWordParameters number) {
        super("Overflow of the constant - \"" + number.getWord() + "\"", number.getPosition(), number.getInput());
    }
}
