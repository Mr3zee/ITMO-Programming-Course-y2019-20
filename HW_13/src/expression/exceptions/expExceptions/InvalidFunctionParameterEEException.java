package expression.exceptions.expExceptions;

public class InvalidFunctionParameterEEException extends EvaluatingExpressionException {
    public InvalidFunctionParameterEEException(String name, int arg) {
        super("Parameter(s) in function \"" + name + "\" is(are) invalid; Parameter: (" + arg + ")");
    }
}
