package expression.type;

public abstract class AbstractEType<T extends Number> implements EType<T> {
    protected T value;

    @Override
    public T value() {
        return value;
    }

    @Override
    public EType<T> add(EType<T> v) {
        return null;
    }

    @Override
    public EType<T> subtract(EType<T> v) {
        return null;
    }

    @Override
    public EType<T> divide(EType<T> v) {
        return null;
    }

    @Override
    public EType<T> multiply(EType<T> v) {
        return null;
    }

    @Override
    public EType<T> negate() {
        return null;
    }
}
