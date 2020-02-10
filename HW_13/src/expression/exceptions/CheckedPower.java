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
        for (int i = 0; i < secondArg; i++) {
            if ((ans > 0 && firstArg > 0 && (Integer.MAX_VALUE / ans) < firstArg) ||
                    (ans < 0 && firstArg < 0 && (Integer.MAX_VALUE / ans) > firstArg)) {
                throw new OverflowEEException("Power", firstArg, secondArg);
            }
            if ((ans > 0 && firstArg < 0 && (Integer.MIN_VALUE / ans) > firstArg) ||
                    (ans != -1 && ans < 0 && firstArg > 0 && (Integer.MIN_VALUE / ans) < firstArg)) {
                throw new UnderflowEEException("Power", firstArg, secondArg);
            }
            ans *= firstArg;
        }
        return ans;
    }
}
