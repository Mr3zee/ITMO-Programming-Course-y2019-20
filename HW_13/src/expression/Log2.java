package expression;

public class Log2 extends UnaryOperations {
    public Log2(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int arg) {
        return (int) (Math.log(arg) / Math.log(2));
    }

    @Override
    protected double toCalculate(double arg) {
        return Math.log(arg) / Math.log(2);
    }

    @Override
    protected String getOperand() {
        return "log2 ";
    }

    @Override
    protected int primary() {
        return 4271;
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
