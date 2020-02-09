package expression;

public class Negate extends UnaryOperations {

    public Negate(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int arg) {
        return -1 * arg;
    }

    @Override
    protected double toCalculate(double arg) {
        return -1.0 * arg;
    }

    @Override
    protected String getOperand() {
        return "-";
    }

    @Override
    protected int primary() {
        return 2027;
    }

    @Override
    public int getPriority() {
        return 30;
    }

    @Override
    public boolean dependsOnOrder() {
        return false;
    }
}
