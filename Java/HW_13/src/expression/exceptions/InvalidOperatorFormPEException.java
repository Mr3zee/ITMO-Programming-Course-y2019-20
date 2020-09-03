package expression.exceptions;

import expression.parser_tools.FoundNextInfo;

public class InvalidOperatorFormPEException extends ParsingExpressionException {
    public InvalidOperatorFormPEException(final String name, final FoundNextInfo found) {
        super("Invalid form of \""+ name + "\" operator, found \"" + found.getNext() + "\"", found.getPosition(), found.getInput());
    }
}