package expression.exception;

import expression.CommonExpression;

public class DivisionByZeroException extends ExpressionException {
    public DivisionByZeroException(CommonExpression expression) {
        super(expression);
    }

    @Override
    public String getMessage() {
        return "DivisionByZeroException in " + getExpression() + "\n";
    }
}

