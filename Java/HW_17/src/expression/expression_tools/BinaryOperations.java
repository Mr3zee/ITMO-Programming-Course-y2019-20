package expression.expression_tools;

import expression.type.EType;

import java.util.Objects;

public abstract class BinaryOperations<T extends Number> implements CommonExpression<T> {
    private CommonExpression<T> firstExp;
    private CommonExpression<T> secondExp;

    public BinaryOperations(CommonExpression<T> firstExp, CommonExpression<T> secondExp) {
        this.firstExp = firstExp;
        this.secondExp = secondExp;
    }

    @Override
    public EType<T> evaluate(EType<T> x) {
        return toCalculate(firstExp.evaluate(x), secondExp.evaluate(x));
    }

    @Override
    public EType<T> evaluate(EType<T> x, EType<T> y, EType<T> z) {
        return toCalculate(firstExp.evaluate(x, y, z), secondExp.evaluate(x, y, z));
    }

    protected abstract EType<T> toCalculate(EType<T> firstArg, EType<T> secondArg);

    @Override
    public String toString() {
        return "(" + firstExp + ' ' + getOperand() + ' ' + secondExp + ")";
    }

    @Override
    public String toMiniString() {
        boolean firstHigherPriority = this.getPriority() > firstExp.getPriority();
        boolean secondHigherPriority = this.getPriority() > secondExp.getPriority() ||
                (this.getPriority() == secondExp.getPriority() && (this.dependsOnOrder() || secondExp.dependsOnOrder()));
        return (firstHigherPriority ? "(" : "") + firstExp.toMiniString() + (firstHigherPriority ? ")" : "")
                + ' ' + getOperand() + ' ' +
                (secondHigherPriority ? "(" : "") + secondExp.toMiniString() + (secondHigherPriority ? ")" : "");
    }

    protected abstract String getOperand();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        BinaryOperations<?> that = (BinaryOperations<?>) o;
        return Objects.equals(firstExp, that.firstExp) &&
                Objects.equals(secondExp, that.secondExp);
    }

    @Override
    public int hashCode() {
        final int MOD = 1073676287;
        final int PRIME = primary();
        int result = 3527;
        result = (PRIME * result + firstExp.hashCode()) % MOD;
        result = (PRIME * result + secondExp.hashCode()) % MOD;
        return result;
    }

    protected abstract int primary();
}
