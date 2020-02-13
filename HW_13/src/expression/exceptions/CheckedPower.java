package expression.exceptions;

import expression.*;
import expression.exceptions.EExceptions.*;

public class CheckedPower extends Power {
    public CheckedPower(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    public int toCalculate(int firstArg, int secondArg) throws EvaluatingExpressionException {
        if (secondArg < 0 || (firstArg == 0 && secondArg == 0)) {
            throw new InvalidFunctionParametersEEException("Power", firstArg, secondArg);
        }
        if (firstArg == 0) {
            return 0;
        }
        if (firstArg == -2 && secondArg == 31) {
            return Integer.MIN_VALUE;
        }
        int ans = 1;
        boolean neg = firstArg < 0 && secondArg % 2 == 1;
        int s = secondArg, f = firstArg < 0 ? -firstArg : firstArg;
        while (s != 0) {
            if ((Integer.MAX_VALUE / ans) < f || (((Integer.MAX_VALUE / f) < f || f == Integer.MIN_VALUE) && s != 1)) {
                if (neg) {
                    throw new UnderflowEEException("Power", firstArg, secondArg);
                }
                throw new OverflowEEException("Power", firstArg, secondArg);
            }
            if (s % 2 == 1) {
                ans *= f;
            }
            s >>= 1;
            f *= f;
        }
        return ans;
    }
}
