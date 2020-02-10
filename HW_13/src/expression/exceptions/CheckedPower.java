package expression.exceptions;

import expression.CommonExpression;
import expression.Power;
import expression.exceptions.expExceptions.InvalidFunctionParametersEEException;
import expression.exceptions.expExceptions.OverflowEEException;
import expression.exceptions.expExceptions.UnderflowEEException;

public class CheckedPower extends Power {
    public CheckedPower(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) {
        if (secondArg < 0 || (firstArg == 0 && secondArg == 0)) {
            throw new InvalidFunctionParametersEEException("Power", firstArg, secondArg);
        }
        if (firstArg == 0) {
            return 0;
        }
        int ans = 1;
        boolean neg = firstArg < 0;
        firstArg = Math.abs(firstArg);
        for (int i = 0; i < secondArg; i++) {
            if ((Integer.MAX_VALUE / ans) < firstArg) {
                if (secondArg % 2 == 0 || !neg) {
                    throw new OverflowEEException("Power", firstArg, secondArg);
                }
                throw new UnderflowEEException("Power", -firstArg, secondArg);
            }
            ans *= firstArg;
        }
        return secondArg % 2 == 0 || !neg ? ans: -ans;
    }
}
