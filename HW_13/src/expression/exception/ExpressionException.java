package expression.exception;

import expression.CommonExpression;

public class ExpressionException extends RuntimeException {
    protected final CommonExpression expression;
    public ExpressionException(CommonExpression expression) {
        this.expression = expression;
    }

    public CommonExpression getExpression() {
        return expression;
    }

    @Override
    public String getMessage() {
        return "ExpressionException in " + getExpression() + "\n";
    }
}
