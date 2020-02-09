package expression.exceptions.expExceptions;

import expression.parser.FoundNextInfo;

public class NoParenthesisPEException extends ParsingExpressionException {
    public NoParenthesisPEException(final String expected, final FoundNextInfo found) {
        super("Expected " + expected + " parenthesis, found '" + found.getNext() + "'", found.getPosition(), found.getInput());
    }
}
