package expression.exceptions;

import expression.parser.ExceptionParameters;

public class MissingWhitespacePEException extends ParsingExpressionException {
    public MissingWhitespacePEException(String lastLexeme, ExceptionParameters foundNext) {
        super("Whitespace symbol expected after \"" + lastLexeme + "\"", foundNext.getPosition(), foundNext.getInput());
    }
}
