package expression.exceptions;

import expression.CommonExpression;
import expression.Multiply;
import expression.exceptions.expExceptions.EvaluatingExpressionException;
import expression.exceptions.expExceptions.OverflowEEException;
import expression.exceptions.expExceptions.UnderflowEEException;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) throws EvaluatingExpressionException {
        if ((firstArg > 0 && secondArg > 0 && (Integer.MAX_VALUE / firstArg) < secondArg) ||
            (firstArg < 0 && secondArg < 0 && (Integer.MAX_VALUE / firstArg) > secondArg)) {
            throw new OverflowEEException("Multiply", firstArg, secondArg);
        }
        if ((firstArg > 0 && secondArg < 0 && (Integer.MIN_VALUE / firstArg) > secondArg) ||
            (firstArg != -1 && firstArg < 0 && secondArg > 0 && (Integer.MIN_VALUE / firstArg) < secondArg)) {
            throw new UnderflowEEException("Multiply", firstArg, secondArg);
        }
        return super.toCalculate(firstArg, secondArg);
    }
}
