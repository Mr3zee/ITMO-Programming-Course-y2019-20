package expression.expression_tools;

import expression.exceptions.EvaluatingExpressionException;
import expression.exceptions.OverflowEEException;
import expression.exceptions.UnderflowEEException;

public class CheckedReverse extends Reverse {
    public CheckedReverse(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int arg) throws EvaluatingExpressionException {
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
