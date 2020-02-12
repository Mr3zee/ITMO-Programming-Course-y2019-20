package expression.parser;

public interface Source {
    char next();
    boolean hasNext();
    ExpressionException error(String massage);
}
