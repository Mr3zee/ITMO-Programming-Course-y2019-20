package expression.exceptions;

import expression.CommonExpression;
import expression.Power;
import expression.exceptions.expExceptions.InvalidFunctionParametersEEException;
import expression.exceptions.expExceptions.OverflowEEException;

public class CheckedPower extends Power {
    public CheckedPower(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) {
        if (firstArg <= 0 || secondArg < 0) {
            throw new InvalidFunctionParametersEEException("Power", firstArg, secondArg);
        }
        int ans = 1;
        for (int i = 0; i < secondArg; i++) {
            if ((Integer.MAX_VALUE / ans) < firstArg) {
                throw new OverflowEEException("Power", firstArg, secondArg);
            }
            ans *= firstArg;
        }
        return ans;
    }
}
