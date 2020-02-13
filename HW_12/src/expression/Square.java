package expression;

public class Square extends UnaryOperations {
    public Square(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int x) {
        return x * x;
    }

    @Override
    protected double toCalculate(double x) {
        return x * x;
    }

    @Override
    protected String getOperand() {
        return "square ";
    }

    @Override
    protected int primary() {
        return 1427;
    }
}
