package expression.exceptions;

import expression.TripleExpression;
import expression.exceptions.expExceptions.ParsingExpressionException;

public interface Parser {
    TripleExpression parse(String expression) throws ParsingExpressionException;
}
