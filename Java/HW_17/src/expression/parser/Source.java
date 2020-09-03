package expression.parser;

public interface Source {
    char next();
    boolean hasNext();
    char end();
}
