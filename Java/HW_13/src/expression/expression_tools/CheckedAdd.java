package expression.expression_tools;

import expression.exceptions.EvaluatingExpressionException;
import expression.exceptions.OverflowEEException;
import expression.exceptions.UnderflowEEException;

public class CheckedAdd extends Add {
    public CheckedAdd(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) throws EvaluatingExpressionException {
        if (firstArg > 0 && Integer.MAX_VALUE - firstArg < secondArg) {
            throw new OverflowEEException("Add", firstArg, secondArg);
        }
        if (firstArg < 0 && Integer.MIN_VALUE - firstArg > secondArg) {
            throw new UnderflowEEException("Add", firstArg, secondArg);
        }
        return super.toCalculate(firstArg, secondArg);
    }
}
