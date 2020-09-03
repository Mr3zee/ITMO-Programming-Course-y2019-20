package expression.expression;

import expression.expression.CommonExpression;
import expression.expression.Subtract;
import expression.exceptions.EvaluatingExpressionException;
import expression.exceptions.OverflowEEException;
import expression.exceptions.UnderflowEEException;

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