package expression.exceptions;

import expression.TripleExpression;
import expression.exceptions.EExceptions.ParsingExpressionException;

public interface Parser<T extends Number> {
    TripleExpression<T> parse(String expression) throws ParsingExpressionException;
}
