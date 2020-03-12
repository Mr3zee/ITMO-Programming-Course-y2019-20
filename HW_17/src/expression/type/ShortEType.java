package expression.type;

import expression.exceptions.*;

public class ShortEType extends AbstractEType<Short> implements ForbiddenDivisionByZero<Short> {
    public ShortEType(Short value) {
        super(value);
    }

    @Override
    protected Short calcAdd(Short v) {
        return (short) (value() + v);
    }

    @Override
    protected Short calcSubtract(Short v) {
        return (short) (value() - v);
    }

    @Override
    protected Short calcMultiply(Short v) {
        return (short) (value() * v);
    }

    @Override
    protected Short calcDivide(Short v) throws DivisionByZeroEException {
        checkDivisionByZero(v);
        return (short) (value() / v);
    }

    @Override
    protected Short calcNegate() {
        return (short) -value();
    }

    @Override
    protected Short calcBitCount() {
        Short v = value();
        return (short) (Integer.bitCount(v) - (v >= 0 ? 0 : 16));
    }

    @Override
    protected Short calcMin(Short v) {
        return (short) Math.min(value(), v);
    }

    @Override
    protected Short calcMax(Short v) {
        return (short) Math.max(value(), v);
    }

    @Override
    public EType<Short> valueOf(Short v) {
        return new ShortEType(v);
    }

    public static ShortEType parseShort(String v) {
        return new ShortEType((short) Integer.parseInt(v));
    }

    @Override
    public Short getZero() {
        return 0;
    }

    @Override
    protected int primary() {
        return 2861;
    }
}
