package expression.type;

import expression.exceptions.DivisionByZeroEException;

import java.util.Objects;

public abstract class AbstractEType<T extends Number> implements EType<T> {
    private T value;

    public AbstractEType(T value) {
        this.value = value;
    }

    @Override
    public T value() {
        return value;
    }

    @Override
    public EType<T> add(EType<T> v) {
        return valueOf(calcAdd(v.value()));
    }

    @Override
    public EType<T> subtract(EType<T> v) {
        return valueOf(calcSubtract(v.value()));
    }

    @Override
    public EType<T> divide(EType<T> v) {
        return valueOf(calcDivide(v.value()));
    }

    @Override
    public EType<T> multiply(EType<T> v) {
        return valueOf(calcMultiply(v.value()));
    }

    @Override
    public EType<T> min(EType<T> v) {
        return valueOf(calcMin(v.value()));
    }

    @Override
    public EType<T> max(EType<T> v) {
        return valueOf(calcMax(v.value()));
    }

    @Override
    public EType<T> bitCount() {
        return valueOf(calcBitCount());
    }

    @Override
    public EType<T> negate() {
        return valueOf(calcNegate());
    }

    protected abstract T calcAdd(T v);

    protected abstract T calcSubtract(T v);

    protected abstract T calcMultiply(T v);

    protected abstract T calcDivide(T v);

    protected abstract T calcNegate();

    protected abstract T calcBitCount();

    protected abstract T calcMin(T v);

    protected abstract T calcMax(T v);

    @Override
    public abstract EType<T> valueOf(T v);

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        AbstractEType<?> that = (AbstractEType<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return (primary() * 2399) % 1073676287;
    }

    protected abstract int primary();

    protected void checkDivisionByZero(T v) {
        if (v.equals(getZero())) {
            throw new DivisionByZeroEException(v.toString());
        }
    }

    protected abstract T getZero();
}
