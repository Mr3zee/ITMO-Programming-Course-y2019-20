package expression.exceptions;

import expression.CommonExpression;
import expression.Reverse;
import expression.exceptions.expExceptions.OverflowEEException;
import expression.exceptions.expExceptions.UnderflowEEException;

public class CheckedReverse extends Reverse {
    public CheckedReverse(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int arg) {
        int i = arg < 0 ? -1 :1;
        try {
            return i * Integer.parseInt(new StringBuilder().append(Math.abs(arg)).reverse().toString());
        } catch (NumberFormatException e) {
            if (i == -1) {
                throw new UnderflowEEException("Reverse", arg);
            }
            throw new OverflowEEException("Reverse", arg);
        }
    }
}
