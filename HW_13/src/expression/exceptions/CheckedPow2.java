package expression.exceptions;

import expression.CommonExpression;
import expression.exceptions.expExceptions.InvalidFunctionParametersEEException;
import expression.exceptions.expExceptions.OverflowEEException;
import expression.Pow2;

public class CheckedPow2 extends Pow2 {
    public CheckedPow2(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int arg) {
        if (arg >= 31) {
            throw new OverflowEEException("Pow2", arg);
        }
        if (arg < 0) {
            throw new InvalidFunctionParametersEEException("Pow2", arg);
        }
        return super.toCalculate(arg);
    }
}