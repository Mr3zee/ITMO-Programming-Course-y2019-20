package expression.exceptions.expExceptions;

public class InvalidFunctionParametersEEException extends EvaluatingExpressionException {
    public InvalidFunctionParametersEEException(String name, int arg) {
        super("Parameter in function \"" + name + "\" is invalid; Parameter: (" + arg + ")");
    }

    public InvalidFunctionParametersEEException(String name, int arg1, int arg2) {
        super("Parameters in function \"" + name + "\" are invalid; Parameter: (" + arg1 + ", " + arg2 + ")");
    }
}
