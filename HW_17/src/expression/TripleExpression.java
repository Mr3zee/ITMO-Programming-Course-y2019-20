package expression;

import expression.type.EType;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface TripleExpression<T extends Number> extends ToMiniString {
    EType<T> evaluate(EType<T> x, EType<T> y, EType<T> z);
}
