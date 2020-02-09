package expression.exceptions;

import expression.CommonExpression;
import expression.Divide;
import expression.exceptions.expExceptions.DivisionByZeroEException;
import expression.exceptions.expExceptions.EvaluatingExpressionException;
import expression.exceptions.expExceptions.OverflowEEException;

public class CheckedDivide extends Divide {
    public CheckedDivide(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) throws EvaluatingExpressionException {
        if (secondArg == 0) {
            throw new DivisionByZeroEException(firstArg);
        }
        if (firstArg == Integer.MIN_VALUE && secondArg == -1) {
            throw new OverflowEEException("Divide", firstArg, secondArg);
        }
        return firstArg / secondArg;
    }
}
