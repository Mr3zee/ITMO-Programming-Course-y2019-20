package expression.exceptions;

public class InvalidFunctionParametersEEException extends EvaluatingExpressionException {
    public InvalidFunctionParametersEEException(String name, int arg) {
        super("Parameter(s) in function \"" + name + "\" is(are) invalid; Parameter(s): (" + arg + ")");
    }

    public InvalidFunctionParametersEEException(String name, int arg1, int arg2) {
        super("Parameter(s) in function \"" + name + "\" is(are) invalid; Parameter(s): (" + arg1 + ", " + arg2 + ")");
    }
}
