package expression.expression;

import expression.expression.CommonExpression;
import expression.expression.Divide;
import expression.exceptions.DivisionByZeroEException;
import expression.exceptions.EvaluatingExpressionException;
import expression.exceptions.OverflowEEException;

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
        return super.toCalculate(firstArg, secondArg);
    }
}
