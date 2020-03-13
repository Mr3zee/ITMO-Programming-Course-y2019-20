package expression.type;

import expression.exceptions.*;

public class IntegerEType extends AbstractEType<Integer> implements ForbiddenDivisionByZero<Integer> {
    public IntegerEType(Integer value) {
        super(value);
    }

    @Override
    protected Integer calcAdd(Integer augend) throws OverflowEEException, UnderflowEEException {
        Integer addend = value();
        if (addend > 0 && Integer.MAX_VALUE - addend < augend) {
            throw new OverflowEEException("Add", addend, augend);
        }
        if (addend < 0 && Integer.MIN_VALUE - addend > augend) {
            throw new UnderflowEEException("Add", addend, augend);
        }
        return addend + augend;
    }

    @Override
    protected Integer calcSubtract(Integer subtrahend) throws OverflowEEException, UnderflowEEException {
        Integer minuend = value();
        if ((minuend > 0 && minuend - Integer.MAX_VALUE > subtrahend) || (minuend == 0 && subtrahend == Integer.MIN_VALUE)) {
            throw new OverflowEEException("Subtract", minuend, subtrahend);
        }
        if (minuend < 0 && minuend - Integer.MIN_VALUE < subtrahend) {
            throw new UnderflowEEException("Subtract", minuend, subtrahend);
        }
        return minuend - subtrahend;
    }

    @Override
    protected Integer calcMultiply(Integer factor) throws OverflowEEException, UnderflowEEException {
        Integer multiplier = value();
        if ((multiplier > 0 && factor > 0 && (Integer.MAX_VALUE / multiplier) < factor) ||
            (multiplier < 0 && factor < 0 && (Integer.MAX_VALUE / multiplier) > factor)) {
            throw new OverflowEEException("Multiply", multiplier, factor);
        }
        if ((multiplier > 0 && factor < 0 && (Integer.MIN_VALUE / multiplier) > factor) ||
            (multiplier != -1 && multiplier < 0 && factor > 0 && (Integer.MIN_VALUE / multiplier) < factor)) {
            throw new UnderflowEEException("Multiply", multiplier, factor);
        }
        return multiplier * factor;
    }

    @Override
    protected Integer calcDivide(Integer divider) throws DivisionByZeroEException, OverflowEEException {
        Integer dividend = value();
        checkDivisionByZero(divider);
        if (dividend == Integer.MIN_VALUE && divider == -1) {
            throw new OverflowEEException("Divide", dividend, divider);
        }
        return dividend / divider;
    }

    @Override
    protected Integer calcNegate() throws OverflowEEException {
        Integer value = value();
        if (value == Integer.MIN_VALUE) {
            throw new OverflowEEException("Negate", value);
        }
        return -value;
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
        return new IntegerEType(v);
    }

    public static EType<Integer> parseInteger(String v) {
        return new IntegerEType(Integer.parseInt(v));
    }

    @Override
    protected int primary() {
        return 2153;
    }

    @Override
    public Integer getZero() {
        return 0;
    }
}
