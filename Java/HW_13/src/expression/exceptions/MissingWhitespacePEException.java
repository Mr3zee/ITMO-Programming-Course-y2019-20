package expression.exceptions;

import expression.parser.FoundNextInfo;

public class MissingWhitespacePEException extends ParsingExpressionException {
    public MissingWhitespacePEException(String name, FoundNextInfo next) {
        super("Whitespace symbol expected after \"" + name + "\"", next.getPosition(), next.getInput());
    }
}