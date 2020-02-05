package expression.parser;

import expression.exception.*;

public interface Source {
    char next();
    boolean hasNext();
    ExpressionException error(String message);
}
