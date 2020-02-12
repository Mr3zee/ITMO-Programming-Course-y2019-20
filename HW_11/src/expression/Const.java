package expression;

import java.util.Objects;

public class Const implements CommonExpression {
    private final Number value;

    public Const(final Number value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return value.intValue();
    }

    @Override
    public double evaluate(double x) {
        return value.doubleValue();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value.intValue();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Const that = (Const) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int getPriority() {
        return 30;
    }

    @Override
    public boolean dependsOnOrder() {
        return false;
    }
}
