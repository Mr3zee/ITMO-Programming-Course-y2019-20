package expression.parser;

public class ExpressionSource implements Source {
    private int pos;
    private final String expression;

    public ExpressionSource(String expression) {
        this.expression = expression;
    }

    @Override
    public char next() {
        return expression.charAt(pos++);
    }

    @Override
    public boolean hasNext() {
        return pos < expression.length();
    }

    @Override
    public ExpressionException error(String massage) {
        return new ExpressionException(pos + ": " + massage);
    }
}
