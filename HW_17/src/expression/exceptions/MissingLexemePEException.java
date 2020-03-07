package expression.exceptions;

import expression.parser.FoundNextInfo;

public class MissingLexemePEException extends ParsingExpressionException {
    public MissingLexemePEException(final String lastLex, final FoundNextInfo found) {
        super("Missing lexeme: last lexeme - \"" + lastLex +
                "\", found - \"" + found.getNext() + "\"", found.getPosition(), found.getInput());
    }
}
