package expression;

public interface CommonExpression<T extends Number> extends ExpressionMy<T>, ExpressionTriple<T> {
    // Primary numbers used for hashCode():
    // Binary ops: 3527
    // Add: 3557
    // Divide: 1213
    // Multiply: 1747
    // Subtract: 2777
    // Unary ops: 2467
    // Negate: 2027
    // Count: 2797
    // Minimum: 3463
    // Maximum: 3469
    // Abstract EType: 2399
    // Integer EType: 2153
    // Double EType: 1637
    // BigInteger EType: 2969
    // Unchecked Integer EType: 2213
    // Long EType: 2551
    // Short EType: 2861

    // Priorities:
    // Min / Max : 5
    // Add / Subtract : 10
    // Multiply / Divide : 20
    // Variable / Const / Negate / Count : 40

    int getPriority();

    boolean dependsOnOrder();
}
