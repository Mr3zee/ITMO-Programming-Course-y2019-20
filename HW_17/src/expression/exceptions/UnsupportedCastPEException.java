package expression.exceptions;

import expression.parser.NextWordParameters;

public class UnsupportedCastPEException extends ParsingExpressionException {
    public UnsupportedCastPEException(final NextWordParameters word) {
        super("Can not cast " + word.getWord() + " (double)", word.getPosition(), word.getInput());
    }
}
