package expression.exceptions;

import expression.parser.NextWordParameters;

public class NoParenthesisPEException extends ParsingExpressionException {
    public NoParenthesisPEException(final String expected, final NextWordParameters found) {
        super("Expected " + expected + " parenthesis, found '" + found.getWord() + "'", found.getPosition(), found.getInput());
    }
}
