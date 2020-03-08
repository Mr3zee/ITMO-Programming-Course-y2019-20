package expression;

import expression.exceptions.IllegalVariableName;
import expression.type.EType;

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
    public EType<T> evaluate(EType<T> x, EType<T> y, EType<T> z) throws IllegalVariableName {
        switch (name) {
            case "x" :
                return x;
            case "y" :
                return y;
            case "z" :
                return z;
            default:
                throw new IllegalVariableName(name);
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
