package expression.type;

import expression.exceptions.EExceptions.*;

public class IntegerEType extends AbstractEType<Integer> {
    public IntegerEType(Integer value) {
        super(value);
    }

    public IntegerEType() {
    }

    @Override
    protected Integer calcAdd(Integer v) {
        Integer value = value();
        if (value > 0 && Integer.MAX_VALUE - value < v) {
            throw new OverflowEEException("Add", value, v);
        }
        if (value < 0 && Integer.MIN_VALUE - value > v) {
            throw new UnderflowEEException("Add", value, v);
        }
        return value() + v;
    }

    @Override
    protected Integer calcSubtract(Integer v) {
        Integer value = value();
        if ((value > 0 && value - Integer.MAX_VALUE > v) || (value == 0 && v == Integer.MIN_VALUE)) {
            throw new OverflowEEException("Subtract", value, v);
        }
        if (value < 0 && value - Integer.MIN_VALUE < v) {
            throw new UnderflowEEException("Subtract", value, v);
        }
        return value() - v;
    }

    @Override
    protected Integer calcMultiply(Integer v) {
        Integer value = value();
        if ((value > 0 && v > 0 && (Integer.MAX_VALUE / value) < v) ||
            (value < 0 && v < 0 && (Integer.MAX_VALUE / value) > v)) {
            throw new OverflowEEException("Multiply", value, v);
        }
        if ((value > 0 && v < 0 && (Integer.MIN_VALUE / value) > v) ||
            (value != -1 && value < 0 && v > 0 && (Integer.MIN_VALUE / value) < v)) {
            throw new UnderflowEEException("Multiply", value, v);
        }
        return value() * v;
    }

    @Override
    protected Integer calcDivide(Integer v) {
        Integer value = value();
        if (v == 0) {
            throw new DivisionByZeroEException(value);
        }
        if (value == Integer.MIN_VALUE && v == -1) {
            throw new OverflowEEException("Divide", value, v);
        }
        return value() / v;
    }

    @Override
    protected Integer calcNegate() {
        Integer value = value();
        if (value == Integer.MIN_VALUE) {
            throw new OverflowEEException("Negate", value);
        }
        return -value;
    }

    @Override
    public EType<Integer> valueOf(Integer v) {
        return new IntegerEType(v);
    }

    @Override
    public EType<Integer> parse(String v) {
        return valueOf(Integer.parseInt(v));
    }
}
