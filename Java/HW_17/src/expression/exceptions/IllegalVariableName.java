package expression.exceptions;

public class IllegalVariableName extends EvaluatingExpressionException {
    public IllegalVariableName(String name) {
        super("Variable has illegal name: " + name);
    }
}
