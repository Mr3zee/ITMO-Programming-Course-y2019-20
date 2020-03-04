package expression;

import expression.type.EType;

import java.util.ArrayList;
import java.util.Objects;

public class Variable<T extends Number> implements CommonExpression<T> {
    private final String name;

    public Variable(final String name) {
        this.name = name;
    }

    @Override
    public EType<T> evaluate(EType<T> x) {
        return x;
    }

    @Override
    public EType<T> evaluate(EType<T> x, EType<T> y, EType<T> z) {
        switch (name) {
            case "x" :
                return x;
            case "y" :
                return y;
            case "z" :
                return z;
            default:
                throw new IllegalArgumentException();
                // TODO: 04.03.2020 exception
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Variable<?> that = (Variable<?>) o;
        // TODO: 04.03.2020 cast
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
