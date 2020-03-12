package expression.exceptions;

import expression.parser.NextWordParameters;

public class UnsupportedCastPEException extends ParsingExpressionException {
    public UnsupportedCastPEException(final NextWordParameters word) {
        super("Unsupported cast: casting " + word.getWord() + ", which is double", word.getPosition(), word.getInput());
    }
}
