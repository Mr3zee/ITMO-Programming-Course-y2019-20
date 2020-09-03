package expression.exceptions;

import expression.parser.FoundNextInfo;

public class IllegalSymbolPEException extends ParsingExpressionException {
    public IllegalSymbolPEException(final FoundNextInfo found) {
        super("Unknown symbol or sequence of symbols - \"" + found.getNext() + "\"", found.getPosition(), found.getInput());
    }
}
