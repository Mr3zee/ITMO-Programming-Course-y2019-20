package expression.parser;

import expression.expression_tools.ExpressionTriple;
import expression.exceptions.ParsingExpressionException;

public interface MyParser<T extends Number> {
    ExpressionTriple<T> parse(String expression) throws ParsingExpressionException;
}
