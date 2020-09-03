package expression.expression_tools;

public class Logarithm extends BinaryOperations {
    public Logarithm(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) {
        int ans = 0;
        while (firstArg != 0) {
            firstArg /= secondArg;
            ans++;
        }
        return ans - 1;
    }

    @Override
    protected double toCalculate(double firstArg, double secondArg) {
        return Math.log(firstArg) / Math.log(secondArg);
    }

    @Override
    protected String getOperand() {
        return "//";
    }

    @Override
    protected int primary() {
        return 5693;
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
