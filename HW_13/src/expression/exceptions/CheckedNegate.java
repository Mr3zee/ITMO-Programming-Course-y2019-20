package expression.exceptions;

import expression.CommonExpression;
import expression.Negate;
import expression.exceptions.expExceptions.EvaluatingExpressionException;
import expression.exceptions.expExceptions.OverflowEEException;

public class CheckedNegate extends Negate {
    public CheckedNegate(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int arg) throws EvaluatingExpressionException {
        if (arg == Integer.MIN_VALUE) {
            throw new OverflowEEException("Negate", arg);
        }
        return -arg;
    }
}