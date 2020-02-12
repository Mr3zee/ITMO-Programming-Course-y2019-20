package expression.exceptions;

import expression.CommonExpression;
import expression.Subtract;
import expression.exceptions.expExceptions.EvaluatingExpressionException;
import expression.exceptions.expExceptions.OverflowEEException;
import expression.exceptions.expExceptions.UnderflowEEException;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) throws EvaluatingExpressionException {
        if ((firstArg > 0 && firstArg - Integer.MAX_VALUE > secondArg) || (firstArg == 0 && secondArg == Integer.MIN_VALUE)) {
            throw new OverflowEEException("Subtract", firstArg, secondArg);
        }
        if (firstArg < 0 && firstArg - Integer.MIN_VALUE < secondArg) {
            throw new UnderflowEEException("Subtract", firstArg, secondArg);
        }
        return super.toCalculate(firstArg, secondArg);
    }
}