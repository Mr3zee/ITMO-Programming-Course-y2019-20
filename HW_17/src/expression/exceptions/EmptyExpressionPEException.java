package expression.exceptions;

public class EmptyExpressionPEException extends ParsingExpressionException {
    public EmptyExpressionPEException() {
        super("Expression is empty or has only whitespaces, nothing found", 0, "");
    }
}
