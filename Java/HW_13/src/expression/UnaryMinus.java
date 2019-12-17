package expression;

public class UnaryMinus extends UnaryOperations {

    public UnaryMinus(CommonExpression expression) {
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
}
