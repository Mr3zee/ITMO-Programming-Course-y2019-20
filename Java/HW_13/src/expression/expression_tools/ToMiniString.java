package expression.expression_tools;

public interface ToMiniString {
    default String toMiniString() {
        return toString();
    }
}
