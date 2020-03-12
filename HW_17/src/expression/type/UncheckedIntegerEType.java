package expression.type;

import expression.exceptions.*;

public class UncheckedIntegerEType extends IntegerEType{
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

    public EType<Integer> valueOf(Long v) {
        return super.valueOf(v.intValue());
    }

    public static EType<Integer> parseUncheckedInteger(String v) {
        return new UncheckedIntegerEType(Long.parseLong(v));
    }

    @Override
    protected int primary() {
        return 2213;
    }
}
