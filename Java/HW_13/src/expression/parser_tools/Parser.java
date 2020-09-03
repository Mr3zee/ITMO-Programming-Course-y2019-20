package expression.parser_tools;

import expression.expression.TripleExpression;
import expression.exceptions.ParsingExpressionException;

public interface Parser {
    TripleExpression parse(String expression) throws ParsingExpressionException;
}
