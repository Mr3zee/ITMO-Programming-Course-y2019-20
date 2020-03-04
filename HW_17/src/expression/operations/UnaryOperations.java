package expression.operations;

import expression.CommonExpression;
import expression.type.EType;

import java.util.Objects;

public abstract class UnaryOperations<T extends Number> implements CommonExpression<T> {
    CommonExpression<T> expression;

    public UnaryOperations(CommonExpression<T> expression) {
        this.expression = expression;
    }

    @Override
    public EType<T> evaluate(EType<T> x) {
        return toCalculate(expression.evaluate(x));
    }

    @Override
    public EType<T> evaluate(EType<T> x, EType<T> y, EType<T> z) {
        return toCalculate(expression.evaluate(x, y, z));
    }

    protected abstract EType<T> toCalculate(EType<T> arg);

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
        UnaryOperations<?> that = (UnaryOperations<?>) o;
        // TODO: 04.03.2020 cast
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
