package expression.exceptions;

import expression.parser.NextWordParameters;

public class InvalidOperatorFormPEException extends ParsingExpressionException {
    public InvalidOperatorFormPEException(final String name, final NextWordParameters found) {
        super("Invalid form of \""+ name + "\" operator, found \"" + found.getWord() + "\"", found.getPosition(), found.getInput());
    }
}
