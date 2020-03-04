package expression;

import expression.type.EType;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Expression<T extends Number> extends ToMiniString {
    EType<T> evaluate(EType<T> x);
}
