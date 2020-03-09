package expression.exceptions;

import expression.parser.NextWordParameters;

public class MissingLexemePEException extends ParsingExpressionException {
    public MissingLexemePEException(final String lastLex, final NextWordParameters found) {
        super("Missing lexeme: last lexeme - \"" + lastLex +
                "\", found - \"" + found.getWord() + "\"", found.getPosition(), found.getInput());
    }
}
