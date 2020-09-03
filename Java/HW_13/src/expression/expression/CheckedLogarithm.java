package expression.expression;

import expression.expression.CommonExpression;
import expression.expression.Logarithm;
import expression.exceptions.EvaluatingExpressionException;
import expression.exceptions.InvalidFunctionParametersEEException;

public class CheckedLogarithm extends Logarithm {
    public CheckedLogarithm(CommonExpression firstExp, CommonExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int toCalculate(int firstArg, int secondArg) throws EvaluatingExpressionException {
        if (secondArg <= 1 || firstArg <= 0) {
            throw new InvalidFunctionParametersEEException("Logarithm", firstArg, secondArg);
        }
        return super.toCalculate(firstArg, secondArg);
    }
}
