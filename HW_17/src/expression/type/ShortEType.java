package expression.type;

import expression.exceptions.DivisionByZeroEException;

public class ShortEType extends AbstractEType<Short> {
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
        if (v == 0) {
            throw new DivisionByZeroEException(v.toString());
        }
        return (short) (value() / v);
    }

    @Override
    protected Short calcNegate() {
        return (short) -value();
    }

    @Override
    protected Short calcBitCount() {
        return (short) Integer.bitCount(value());
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
    protected int primary() {
        return 2861;
    }
}
