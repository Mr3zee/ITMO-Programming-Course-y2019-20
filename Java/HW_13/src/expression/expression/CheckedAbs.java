package expression.expression;

import expression.expression.CommonExpression;
import expression.exceptions.EvaluatingExpressionException;
import expression.exceptions.OverflowEEException;

public class CheckedAbs extends Abs {
    public CheckedAbs(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int x) throws EvaluatingExpressionException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowEEException("Abs", x);
        }
        return super.toCalculate(x);
    }
}
