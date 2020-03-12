package expression.exceptions;

import expression.parser.ExceptionParameters;

public class NoParenthesisPEException extends ParsingExpressionException {
    public NoParenthesisPEException(final String expected, final ExceptionParameters found) {
        super("Expected " + expected + " parenthesis, found '" + found.getWord() + "'", found.getPosition(), found.getInput());
    }
}
