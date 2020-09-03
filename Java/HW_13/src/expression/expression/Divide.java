package expression.expression;

public class Divide extends BinaryOperations {
    public Divide(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) {
        return firstArg / secondArg;
    }

    @Override
    protected double toCalculate(double firstArg, double secondArg) {
        return firstArg / secondArg;
    }

    @Override
    protected String getOperand() {
        return "/";
    }

    @Override
    protected int primary() {
        return 1213;
    }

    @Override
    public int getPriority() {
        return 20;
    }

    @Override
    public boolean dependsOnOrder() {
        return true;
    }
}
