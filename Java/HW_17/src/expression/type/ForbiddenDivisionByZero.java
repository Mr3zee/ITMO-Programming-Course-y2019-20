package expression.type;

import expression.exceptions.DivisionByZeroEException;

public interface ForbiddenDivisionByZero<T extends Number> {
    default void checkDivisionByZero(T v) {
        if (v.equals(getZero())) {
            throw new DivisionByZeroEException(v.toString());
        }
    }

    T getZero();
}
