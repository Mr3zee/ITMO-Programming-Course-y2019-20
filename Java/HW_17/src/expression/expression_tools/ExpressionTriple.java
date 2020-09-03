package expression.expression_tools;

import expression.type.EType;

public interface ExpressionTriple<T extends Number> extends ToMiniString {
    EType<T> evaluate(EType<T> x, EType<T> y, EType<T> z);
}
