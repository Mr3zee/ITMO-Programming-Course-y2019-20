package expression;

public interface CommonExpression extends Expression, DoubleExpression, TripleExpression {
    //Primary took: 1607, 3557, 1213, 1747, 1427, 2777, 2027, 2083, 3389
    int getPriority();
    boolean dependsOnOrder();
}
