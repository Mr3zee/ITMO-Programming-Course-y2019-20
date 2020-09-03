package expression.exceptions;

import expression.parser.ExceptionParameters;

public class ConstantUnderflowPEException extends ParsingExpressionException {
    public ConstantUnderflowPEException(final ExceptionParameters number) {
        super("Underflow of the constant - \"" + number.getWord() + "\"", number.getPosition(), number.getInput());
    }
}
