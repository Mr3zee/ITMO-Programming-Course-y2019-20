package expression.type;

import expression.exceptions.*;

public class UncheckedIntegerEType extends AbstractEType<Integer> implements ForbiddenDivisionByZero<Integer> {
    public UncheckedIntegerEType(Long value) {
        super(value.intValue());
    }

    @Override
    protected Integer calcAdd(Integer v) {
        return value() + v;
    }

    @Override
    protected Integer calcSubtract(Integer v) {
        return value() - v;
    }

    @Override
    protected Integer calcMultiply(Integer v) {
        return value() * v;
    }

    @Override
    protected Integer calcDivide(Integer v) throws DivisionByZeroEException {
        checkDivisionByZero(v);
        return value() / v;
    }

    @Override
    protected Integer calcNegate() {
        return -value();
    }

    @Override
    protected Integer calcBitCount() {
        return Integer.bitCount(value());
    }

    @Override
    protected Integer calcMin(Integer v) {
        return Math.min(value(), v);
    }

    @Override
    protected Integer calcMax(Integer v) {
        return Math.max(value(), v);
    }

    @Override
    public EType<Integer> valueOf(Integer v) {
        return new UncheckedIntegerEType((long) v);
    }

    public static EType<Integer> parseUncheckedInteger(String v) {
        return new UncheckedIntegerEType(Long.parseLong(v));
    }

    @Override
    protected int primary() {
        return 2213;
    }

    @Override
    public Integer getZero() {
        return 0;
    }
}
