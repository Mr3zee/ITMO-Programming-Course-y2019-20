package expression.exceptions;

import expression.parser.NextWordParameters;

public class MissingWhitespacePEException extends ParsingExpressionException {
    public MissingWhitespacePEException(String lastLexeme, NextWordParameters foundNext) {
        super("Whitespace symbol expected after \"" + lastLexeme + "\"", foundNext.getPosition(), foundNext.getInput());
    }
}
