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
    public char end() {
        pos++;
        return '\0';
    }

    @Override
    public boolean hasNext() {
        return pos < expression.length();
    }

    public int getPosition() {
        return pos;
    }

    public String getExpression() {
        return expression;
    }

    public void rollBack(int v) {
        pos -= v;
    }
}
