package expression.exceptions;

import expression.parser.ExceptionParameters;

public class MissingLexemePEException extends ParsingExpressionException {
    public MissingLexemePEException(final String lastLex, final ExceptionParameters found) {
        super("Missing lexeme: last lexeme - \"" + lastLex +
                "\", found - \"" + found.getWord() + "\"", found.getPosition(), found.getInput());
    }
}
