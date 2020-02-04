package expression;

public class Add extends BinaryOperations {

    public Add(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) {
        return firstArg + secondArg;
    }

    @Override
    protected double toCalculate(double firstArg, double secondArg) {
        return firstArg + secondArg;
    }

    @Override
    protected String getOperand() {
        return " + ";
    }

    @Override
    protected int primary() {
        return 3557;
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public boolean dependsOnOrder() {
        return false;
    }
}
