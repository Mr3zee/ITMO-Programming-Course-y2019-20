package expression;

public class Power extends BinaryOperations {
    public Power(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) {
        return (int) Math.pow(firstArg, secondArg);
    }

    @Override
    protected double toCalculate(double firstArg, double secondArg) {
        return Math.pow(firstArg, secondArg);
    }

    @Override
    protected String getOperand() {
        return "**";
    }

    @Override
    protected int primary() {
        return 6827;
    }

    @Override
    public int getPriority() {
        return 30;
    }

    @Override
    public boolean dependsOnOrder() {
        return true;
    }
}
