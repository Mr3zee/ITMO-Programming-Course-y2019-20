package expression;

import java.util.Objects;

public abstract class BinaryOperations implements CommonExpression {
    private CommonExpression firstExp;
    private CommonExpression secondExp;

    public BinaryOperations(CommonExpression firstExp, CommonExpression secondExp) {
        this.firstExp = firstExp;
        this.secondExp = secondExp;
    }

    @Override
    public int evaluate(int x) {
        return toCalculate(firstExp.evaluate(x), secondExp.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return toCalculate(firstExp.evaluate(x), secondExp.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return toCalculate(firstExp.evaluate(x, y, z), secondExp.evaluate(x, y, z));
    }

    protected abstract int toCalculate(int firstArg, int secondArg);

    protected abstract double toCalculate(double firstArg, double secondArg);

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
        BinaryOperations that = (BinaryOperations) o;
        return Objects.equals(firstExp, that.firstExp) &&
                Objects.equals(secondExp, that.secondExp);
    }

    @Override
    public int hashCode() {
        final int MOD = 1073676287;
        final int PRIME = primary();
        int result = 1;
        result = (PRIME * result + firstExp.hashCode()) % MOD;
        result = (PRIME * result + secondExp.hashCode()) % MOD;
        return result;
    }

    protected abstract int primary();
}
