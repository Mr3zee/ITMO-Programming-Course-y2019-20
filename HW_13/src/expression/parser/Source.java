package expression.parser;

import expression.exception.ExpressionException;

public interface Source {
    char next();
    boolean hasNext();
    ExpressionException error(String massage);
}
