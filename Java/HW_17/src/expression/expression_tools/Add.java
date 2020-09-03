package expression.expression_tools;

import expression.type.EType;

public class Add<T extends Number> extends BinaryOperations<T> {

    public Add(CommonExpression<T> firstExp, CommonExpression<T> secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected EType<T> toCalculate(EType<T> firstArg, EType<T> secondArg) {
        return firstArg.add(secondArg);
    }

    @Override
    protected String getOperand() {
        return "+";
    }

    @Override
    protected int primary() {
        return 3557;
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public boolean dependsOnOrder() {
        return false;
    }
}
