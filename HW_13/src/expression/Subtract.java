package expression;

import javax.swing.plaf.TreeUI;

public class Subtract extends BinaryOperations {

    public Subtract(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) {
        return firstArg - secondArg;
    }

    @Override
    protected double toCalculate(double firstArg, double secondArg) {
        return firstArg - secondArg;
    }

    @Override
    protected String getOperand() {
        return "-";
    }

    @Override
    protected int primary() {
        return 2777;
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public boolean dependsOnOrder() {
        return true;
    }
}
