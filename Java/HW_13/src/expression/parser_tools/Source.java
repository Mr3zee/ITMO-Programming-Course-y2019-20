package expression.parser_tools;

public interface Source {
    char next();
    boolean hasNext();
    char end();
}
