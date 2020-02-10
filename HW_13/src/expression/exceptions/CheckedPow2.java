package expression.exceptions;

import expression.CommonExpression;
import expression.exceptions.expExceptions.OverflowEEException;
import expression.parser.Pow2;

public class CheckedPow2 extends Pow2 {
    public CheckedPow2(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int arg) {
        if (arg >= 32) {
            throw new OverflowEEException("Pow2", arg);
        }
        return super.toCalculate(arg);
    }
}
