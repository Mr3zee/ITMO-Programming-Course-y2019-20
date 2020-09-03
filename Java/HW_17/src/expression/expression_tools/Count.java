package expression.expression_tools;

import expression.type.EType;

public class Count<T extends Number> extends UnaryOperations<T> {
    public Count(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.bitCount();
    }

    @Override
    protected String getOperand() {
        return "count ";
    }

    @Override
    protected int primary() {
        return 2797;
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
