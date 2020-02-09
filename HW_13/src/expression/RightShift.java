package expression;

public class RightShift extends BinaryOperations {
    public RightShift(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) {
        return firstArg >> secondArg;
    }

    @Override
    protected double toCalculate(double firstArg, double secondArg) {
        throw new AssertionError("Operation \"Right Shift\" can not be applied to double");
    }

    @Override
    protected String getOperand() {
        return " >> ";
    }

    @Override
    protected int primary() {
        return 3389;
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
