package expression.expression_tools;

import expression.type.EType;

public class Subtract<T extends Number> extends BinaryOperations<T> {

    public Subtract(CommonExpression<T> firstExp, CommonExpression<T> secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected EType<T> toCalculate(EType<T> firstArg, EType<T> secondArg) {
        return firstArg.subtract(secondArg);
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
