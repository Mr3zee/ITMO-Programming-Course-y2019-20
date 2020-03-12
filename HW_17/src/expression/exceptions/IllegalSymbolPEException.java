package expression.exceptions;

import expression.parser.ExceptionParameters;

public class IllegalSymbolPEException extends ParsingExpressionException {
    public IllegalSymbolPEException(final ExceptionParameters found) {
        super("Unknown symbol or sequence of symbols - \"" + found.getWord() + "\"", found.getPosition(), found.getInput());
    }
}
