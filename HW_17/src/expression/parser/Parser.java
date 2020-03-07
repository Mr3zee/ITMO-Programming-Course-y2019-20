package expression.parser;

import expression.TripleExpression;
import expression.exceptions.ParsingExpressionException;

public interface Parser<T extends Number> {
    TripleExpression<T> parse(String expression) throws ParsingExpressionException;
}
