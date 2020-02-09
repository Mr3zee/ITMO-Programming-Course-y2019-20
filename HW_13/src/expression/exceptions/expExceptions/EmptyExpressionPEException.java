package expression.exceptions.expExceptions;

public class EmptyExpressionPEException extends ParsingExpressionException {
    public EmptyExpressionPEException() {
        super("Expression is empty or has only whitespaces, nothing found", 0, "");
    }
}
