package expression.parser;

import expression.exceptions.expExceptions.*;

public interface Source {
    char next();
    boolean hasNext();
    ExpressionException error(String message);
    char end();
}
