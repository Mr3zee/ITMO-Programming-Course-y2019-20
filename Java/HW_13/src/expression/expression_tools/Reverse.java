package expression.expression_tools;

public class Reverse extends UnaryOperations {
    public Reverse(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int arg) {
        boolean i = arg < 0;
        return Integer.parseInt((i ? "-" : "") + new StringBuilder().append(Math.abs(arg)).reverse().toString());
    }

    @Override
    protected double toCalculate(double arg) {
        boolean i = arg < 0;
        return Double.parseDouble((i ? "-" : "") + new StringBuilder().append(Math.abs(arg)).reverse().toString());
    }

    @Override
    protected String getOperand() {
        return "reverse ";
    }

    @Override
    protected int primary() {
        return 7027;
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
