package expression.exceptions;

import expression.CommonExpression;
import expression.Log2;
import expression.exceptions.EExceptions.EvaluatingExpressionException;
import expression.exceptions.EExceptions.InvalidFunctionParametersEEException;

public class CheckedLog2 extends Log2 {
    public CheckedLog2(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int toCalculate(int arg) throws EvaluatingExpressionException {
        if (arg <= 0) {
            throw new InvalidFunctionParametersEEException("Log2", arg);
        }
        return super.toCalculate(arg);
    }
}
