package expression.exceptions.EExceptions;

public class UnsupportedETypeException extends ExpressionException {
    public UnsupportedETypeException(String type) {
        super("Unsupported type provided: " + type);
    }
}
