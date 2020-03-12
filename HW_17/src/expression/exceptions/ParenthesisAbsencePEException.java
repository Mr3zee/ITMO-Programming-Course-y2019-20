package expression.exceptions;

import expression.parser.ExceptionParameters;

public class ParenthesisAbsencePEException extends ParsingExpressionException {
    public ParenthesisAbsencePEException(final String expected, final ExceptionParameters found) {
        super("Expected " + expected + " parenthesis, found '" + found.getWord() + "'", found.getPosition(), found.getInput());
    }
}
