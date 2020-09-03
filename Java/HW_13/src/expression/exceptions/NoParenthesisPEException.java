package expression.exceptions;

import expression.parser_tools.FoundNextInfo;

public class NoParenthesisPEException extends ParsingExpressionException {
    public NoParenthesisPEException(final String expected, final FoundNextInfo found) {
        super("Expected " + expected + " parenthesis, found '" + found.getNext() + "'", found.getPosition(), found.getInput());
    }
}
