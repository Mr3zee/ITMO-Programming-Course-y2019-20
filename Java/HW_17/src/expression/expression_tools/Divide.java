package expression.expression_tools;

import expression.type.EType;

public class Divide<T extends Number> extends BinaryOperations<T> {
    public Divide(CommonExpression<T> firstExp, CommonExpression<T> secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected EType<T> toCalculate(EType<T> firstArg, EType<T> secondArg) {
        return firstArg.divide(secondArg);
    }

    @Override
    protected String getOperand() {
        return "/";
    }

    @Override
    protected int primary() {
        return 1213;
    }

    @Override
    public int getPriority() {
        return 20;
    }

    @Override
    public boolean dependsOnOrder() {
        return true;
    }
}
