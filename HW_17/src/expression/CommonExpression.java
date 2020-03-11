package expression;

public interface CommonExpression<T extends Number> extends Expression<T>, TripleExpression<T> {
    // Primary numbers used for hashCode():
    // Binary ops: 3527
    // Add: 3557
    // Divide: 1213
    // Multiply: 1747
    // Subtract: 2777
    // Unary ops: 2467
    // Negate: 2027
    // Abstract EType: 2399
    // Integer EType: 2153
    // Double EType: 1637
    // BigInteger EType: 2969
    // Count: 2797

    int getPriority();

    boolean dependsOnOrder();
}
