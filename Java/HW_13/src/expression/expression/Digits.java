package expression.expression;

public class Digits extends UnaryOperations {
    public Digits(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int arg) {
        String num = String.valueOf(arg);
        int ans = 0;
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) != '-') {
                ans += Character.digit(num.charAt(i), 10);
            }
        }
        return ans;
    }

    @Override
    protected double toCalculate(double arg) {
        String num = String.valueOf(arg);
        int ans = 0;
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) != '-' && num.charAt(i) != '.') {
                ans += Character.digit(num.charAt(i), 10);
            }
        }
        return ans;
    }

    @Override
    protected String getOperand() {
        return "digits ";
    }

    @Override
    protected int primary() {
        return 3037;
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
