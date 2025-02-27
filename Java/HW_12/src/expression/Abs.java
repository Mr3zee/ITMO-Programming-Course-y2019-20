package expression;

public class Abs extends UnaryOperations {
    public Abs(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int x) {
        return Math.abs(x);
    }

    @Override
    protected double toCalculate(double x) {
        return Math.abs(x);
    }

    @Override
    protected String getOperand() {
        return "abs ";
    }

    @Override
    protected int primary() {
        return 1607;
    }
}
