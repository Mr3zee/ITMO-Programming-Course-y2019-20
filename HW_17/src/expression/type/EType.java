package expression.type;

public interface EType<T extends Number> {

    T value();

    EType<T> add(EType<T> v);

    EType<T> subtract(EType<T> v);

    EType<T> divide(EType<T> v);

    EType<T> multiply(EType<T> v);

    EType<T> negate();

    EType<T> valueOf(T v);
}
