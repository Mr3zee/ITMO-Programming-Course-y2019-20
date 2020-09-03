package expression.expression_tools;

import expression.exceptions.EvaluatingExpressionException;
import expression.exceptions.OverflowEEException;

public class CheckedNegate extends Negate {
    public CheckedNegate(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int arg) throws EvaluatingExpressionException {
        if (arg == Integer.MIN_VALUE) {
            throw new OverflowEEException("Negate", arg);
        }
        return super.toCalculate(arg);
    }
}
