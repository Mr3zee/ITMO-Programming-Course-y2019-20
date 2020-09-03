package expression.expression_tools;

import java.util.Objects;

public abstract class UnaryOperations implements CommonExpression {
    CommonExpression expression;

    public UnaryOperations(CommonExpression expression) {
        this.expression = expression;
    }

    @Override
    public double evaluate(double x) {
        return toCalculate(expression.evaluate(x));
    }

    @Override
    public int evaluate(int x) {
        return toCalculate(expression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return toCalculate(expression.evaluate(x, y, z));
    }

    protected abstract int toCalculate(int arg);

    protected abstract double toCalculate(double arg);

    @Override
    public String toString() {
        return "(" + getOperand() + expression + ")";
    }

    @Override
    public String toMiniString() {
        return getOperand() + expression;
    }

    protected abstract String getOperand();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        UnaryOperations that = (UnaryOperations) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        final int MOD = 1073676287;
        final int PRIME = primary();
        return (PRIME + expression.hashCode()) % MOD;
    }

    protected abstract int primary();
}
