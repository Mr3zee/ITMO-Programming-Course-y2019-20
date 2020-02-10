package expression;

public class Reverse extends UnaryOperations {
    public Reverse(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int arg) {
        int i = arg < 0 ? -1 :1;
        return i * Integer.parseInt(new StringBuilder().append(Math.abs(arg)).reverse().toString());
    }

    @Override
    protected double toCalculate(double arg) {
        int i = arg < 0 ? -1 :1;
        return i * Double.parseDouble(new StringBuilder().append(Math.abs(arg)).reverse().toString());
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
