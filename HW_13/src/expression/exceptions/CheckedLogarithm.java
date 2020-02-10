package expression.exceptions;

import expression.CommonExpression;
import expression.Logarithm;
import expression.exceptions.expExceptions.InvalidFunctionParametersEEException;

public class CheckedLogarithm extends Logarithm {
    public CheckedLogarithm(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) {
        if (secondArg <= 1 || firstArg <= 0) {
            throw new InvalidFunctionParametersEEException("Logarithm", firstArg, secondArg);
        }
        return super.toCalculate(firstArg, secondArg);
    }
}
