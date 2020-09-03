package expression.exceptions;

import expression.parser.ExceptionParameters;

public class UnsupportedCastPEException extends ParsingExpressionException {
    public UnsupportedCastPEException(final ExceptionParameters word) {
        super("Unsupported cast: casting " + word.getWord() + ", which is double", word.getPosition(), word.getInput());
    }
}
