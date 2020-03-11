package expression.exceptions;

import expression.parser.NextWordParameters;

public class MissingWhitespacePEException extends ParsingExpressionException {
    public MissingWhitespacePEException(String name, NextWordParameters next) {
        super("Whitespace symbol expected after \"" + name + "\"", next.getPosition(), next.getInput());
    }
}
