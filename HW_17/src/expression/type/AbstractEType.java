package expression.type;

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
    public EType<T> negate() {
        return valueOf(calcNegate());
    }

    protected abstract T calcAdd(T v);

    protected abstract T calcSubtract(T v);

    protected abstract T calcMultiply(T v);

    protected abstract T calcDivide(T v);

    protected abstract T calcNegate();

    @Override
    public abstract EType<T> valueOf(T v);

    @Override
    public String toString() {
        return value.toString();
    }
}
