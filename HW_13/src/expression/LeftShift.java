package expression;

public class LeftShift extends BinaryOperations {
    public LeftShift(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) {
        return firstArg << secondArg;
    }

    @Override
    protected double toCalculate(double firstArg, double secondArg) {
        throw new AssertionError("Operation \"Left Shift\" can not be applied to double");
    }

    @Override
    protected String getOperand() {
        return "<<";
    }

    @Override
    protected int primary() {
        return 2083;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean dependsOnOrder() {
        return false;
    }
}
