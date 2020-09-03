package expression.operations;

import expression.CommonExpression;
import expression.type.EType;

public class Maximum<T extends Number> extends BinaryOperations<T> {
    public Maximum(CommonExpression<T> firstExp, CommonExpression<T> secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected EType<T> toCalculate(EType<T> firstArg, EType<T> secondArg) {
        return firstArg.max(secondArg);
    }

    @Override
    protected String getOperand() {
        return "max";
    }

    @Override
    protected int primary() {
        return 3469;
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
