package expression.expression_tools;

import expression.exceptions.EvaluatingExpressionException;
import expression.exceptions.InvalidFunctionParametersEEException;
import expression.exceptions.OverflowEEException;

public class CheckedPow2 extends Pow2 {
    public CheckedPow2(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int arg) throws EvaluatingExpressionException {
        if (arg >= 31) {
            throw new OverflowEEException("Pow2", arg);
        }
        if (arg < 0) {
            throw new InvalidFunctionParametersEEException("Pow2", arg);
        }
        return super.toCalculate(arg);
    }
}
