package expression.exceptions;

public class UnsupportedETypeException extends ExpressionException {
    public UnsupportedETypeException(String type) {
        super("Unsupported type provided: " + type);
    }
}
