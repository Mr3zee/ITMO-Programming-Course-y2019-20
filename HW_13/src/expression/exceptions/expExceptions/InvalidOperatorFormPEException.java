package expression.exceptions.expExceptions;

import expression.parser.FoundNextInfo;

public class InvalidOperatorFormPEException extends ParsingExpressionException {
    public InvalidOperatorFormPEException(final String name, final FoundNextInfo found) {
        super("Invalid form of \""+ name + "\" operator, found \"" + found.getNext() + "\"", found.getPosition(), found.getInput());
    }
}
