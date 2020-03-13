package expression.type;

import expression.exceptions.*;

public class IntegerEType extends UncheckedIntegerEType {
    public IntegerEType(Integer value) {
        super(value);
    }

    protected Integer calcAdd(Integer augend) throws OverflowEEException, UnderflowEEException {
        checkAddOverflow(value(), augend);
        return super.calcAdd(augend);
    }

    protected Integer calcSubtract(Integer subtrahend) throws OverflowEEException, UnderflowEEException {
        checkSubtractOverflow(value(), subtrahend);
        return super.calcSubtract(subtrahend);
    }

    protected Integer calcMultiply(Integer factor) throws OverflowEEException, UnderflowEEException {
        checkMultiplyOverflow(value(), factor);
        return super.calcMultiply(factor);
    }

    protected Integer calcDivide(Integer divider) throws DivisionByZeroEException, OverflowEEException {
        checkDivisionByZero(divider);
        checkDivideOverflow(value(), divider);
        return super.calcDivide(divider);
    }

    protected Integer calcNegate() throws OverflowEEException {
        checkNegateOverFlow(value());
        return super.calcNegate();
    }

    private static void checkAddOverflow(Integer addend, Integer augend) throws OverflowEEException, UnderflowEEException {
        if (addend > 0 && Integer.MAX_VALUE - addend < augend) {
            throw new OverflowEEException("Add", addend, augend);
        }
        if (addend < 0 && Integer.MIN_VALUE - addend > augend) {
            throw new UnderflowEEException("Add", addend, augend);
        }
    }

    private static void checkSubtractOverflow(Integer minuend, Integer subtrahend) throws OverflowEEException, UnderflowEEException {
        if ((minuend > 0 && minuend - Integer.MAX_VALUE > subtrahend) || (minuend == 0 && subtrahend == Integer.MIN_VALUE)) {
            throw new OverflowEEException("Subtract", minuend, subtrahend);
        }
        if (minuend < 0 && minuend - Integer.MIN_VALUE < subtrahend) {
            throw new UnderflowEEException("Subtract", minuend, subtrahend);
        }
    }

    private static void checkMultiplyOverflow(Integer multiplier, Integer factor) throws OverflowEEException, UnderflowEEException {
        int valMax = Integer.MAX_VALUE / multiplier;
        if ((multiplier > 0 && factor > 0 && valMax < factor) ||
                (multiplier < 0 && factor < 0 && valMax > factor)) {
            throw new OverflowEEException("Multiply", multiplier, factor);
        }
        int valMin = Integer.MIN_VALUE / multiplier;
        if ((multiplier > 0 && factor < 0 && valMin > factor) ||
                (multiplier != -1 && multiplier < 0 && factor > 0 && valMin < factor)) {
            throw new UnderflowEEException("Multiply", multiplier, factor);
        }
    }

    private static void checkDivideOverflow(Integer dividend, Integer divider) throws OverflowEEException {
        if (dividend == Integer.MIN_VALUE && divider == -1) {
            throw new OverflowEEException("Divide", dividend, divider);
        }
    }

    private static void checkNegateOverFlow(Integer value) throws OverflowEEException {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowEEException("Negate", value);
        }
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
}
