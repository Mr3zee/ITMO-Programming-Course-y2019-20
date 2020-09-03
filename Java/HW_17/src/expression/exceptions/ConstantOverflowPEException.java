package expression.exceptions;

import expression.parser.ExceptionParameters;

public class ConstantOverflowPEException extends ParsingExpressionException {
    public ConstantOverflowPEException(final ExceptionParameters number) {
        super("Overflow of the constant - \"" + number.getWord() + "\"", number.getPosition(), number.getInput());
    }
}
