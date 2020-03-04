package expression;

import expression.type.EType;

import java.util.Objects;

public class Const<T extends Number> implements CommonExpression<T> {
    private final EType<T> value;

    public Const(final EType<T> value) {
        this.value = value;
    }

    @Override
    public EType<T> evaluate(EType<T> x) {
        return value;
    }

    @Override
    public EType<T> evaluate(EType<T> x, EType<T> y, EType<T> z) {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Const<?> that = (Const<?>) o;
        // TODO: 04.03.2020 cast
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int getPriority() {
        return 40;
    }

    @Override
    public boolean dependsOnOrder() {
        return false;
    }
}
