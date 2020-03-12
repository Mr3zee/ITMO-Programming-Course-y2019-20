package expression.exceptions;

import expression.parser.NextWordParameters;

public class ConstantUnderflowPEException extends ParsingExpressionException {
    public ConstantUnderflowPEException(final NextWordParameters number) {
        super("Underflow of the constant - \"" + number.getWord() + "\"", number.getPosition(), number.getInput());
    }
}
