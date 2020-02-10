package expression;

public class Pow2 extends UnaryOperations {
    public Pow2(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int arg) {
        return (int) Math.pow(2, arg);
    }

    @Override
    protected double toCalculate(double arg) {
        return Math.pow(2, arg);
    }

    @Override
    protected String getOperand() {
        return "pow2 ";
    }

    @Override
    protected int primary() {
        return 4079;
    }

    @Override
    public int getPriority() {
        return 40;
    }

    @Override
    public boolean dependsOnOrder() {
        return false;
    }
}
