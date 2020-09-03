package expression.expression_tools;

import expression.type.EType;

public class Multiply<T extends Number> extends BinaryOperations<T> {
    public Multiply(CommonExpression<T> firstExp, CommonExpression<T> secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected EType<T> toCalculate(EType<T> firstArg, EType<T> secondArg) {
        return firstArg.multiply(secondArg);
    }

    @Override
    protected String getOperand() {
        return "*";
    }

    @Override
    protected int primary() {
        return 1747;
    }

    @Override
    public int getPriority() {
        return 20;
    }

    @Override
    public boolean dependsOnOrder() {
        return false;
    }
}
