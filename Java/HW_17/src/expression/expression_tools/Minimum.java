package expression.expression_tools;

import expression.type.EType;

public class Minimum<T extends Number> extends BinaryOperations<T> {
    public Minimum(CommonExpression<T> firstExp, CommonExpression<T> secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected EType<T> toCalculate(EType<T> firstArg, EType<T> secondArg) {
        return firstArg.min(secondArg);
    }

    @Override
    protected String getOperand() {
        return "min";
    }

    @Override
    protected int primary() {
        return 3463;
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean dependsOnOrder() {
        return false;
    }
}
