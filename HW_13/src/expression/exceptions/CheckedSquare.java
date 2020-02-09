package expression.exceptions;

import expression.CommonExpression;
import expression.Square;
import expression.exceptions.expExceptions.EvaluatingExpressionException;
import expression.exceptions.expExceptions.OverflowEEException;

public class CheckedSquare extends Square {
    public CheckedSquare(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int x) throws EvaluatingExpressionException {
        if ((x == Integer.MIN_VALUE) || (x > 0 && Integer.MAX_VALUE / x < x) || (x < 0 && Integer.MAX_VALUE / x > x)) {
            throw new OverflowEEException("Square", x);
        }
        return x * x;
    }
}
