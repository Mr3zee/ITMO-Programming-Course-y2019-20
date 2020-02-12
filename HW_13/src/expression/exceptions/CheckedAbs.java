package expression.exceptions;

import expression.Abs;
import expression.CommonExpression;
import expression.exceptions.expExceptions.EvaluatingExpressionException;
import expression.exceptions.expExceptions.OverflowEEException;

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
