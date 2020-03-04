package expression;

public interface CommonExpression<T extends Number> extends Expression<T>, TripleExpression<T> {
    //Primary took: 1607, 3557, 1213, 1747, 1427, 2777, 2027, 2083, 3389, 4271, 4079, 5693, 6827, 7027, 3037
    int getPriority();
    boolean dependsOnOrder();
}
