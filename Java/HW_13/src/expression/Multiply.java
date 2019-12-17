package expression;

public class Multiply extends BinaryOperations {
    public Multiply(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) {
        return firstArg * secondArg;
    }

    @Override
    protected double toCalculate(double firstArg, double secondArg) {
        return firstArg * secondArg;
    }

    @Override
    protected String getOperand() {
        return " * ";
    }

    @Override
    protected int primary() {
        return 1747;
    }
}
