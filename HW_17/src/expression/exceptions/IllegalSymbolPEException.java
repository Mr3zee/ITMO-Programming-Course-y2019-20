package expression.exceptions;

import expression.parser.NextWordParameters;

public class IllegalSymbolPEException extends ParsingExpressionException {
    public IllegalSymbolPEException(final NextWordParameters found) {
        super("Unknown symbol or sequence of symbols - \"" + found.getWord() + "\"", found.getPosition(), found.getInput());
    }
}
