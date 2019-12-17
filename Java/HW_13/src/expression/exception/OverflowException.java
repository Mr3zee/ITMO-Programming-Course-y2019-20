package expression.exception;

import expression.CommonExpression;

public class OverflowException extends ExpressionException {
    public OverflowException(CommonExpression expression) {
        super(expression);
    }

    @Override
    public String getMessage() {
        return "OverflowException in " + getExpression() + "\n";
    }
}
