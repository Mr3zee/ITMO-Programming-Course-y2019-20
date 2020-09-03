package expression.parser;

import expression.expression_tools.TripleExpression;
import expression.exceptions.ParsingExpressionException;

public interface Parser {
    TripleExpression parse(String expression) throws ParsingExpressionException;
}
