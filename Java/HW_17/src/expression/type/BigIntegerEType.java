package expression.type;

import expression.exceptions.DivisionByZeroEException;

import java.math.BigInteger;

public class BigIntegerEType extends AbstractEType<BigInteger> implements ForbiddenDivisionByZero<BigInteger> {
    public BigIntegerEType(BigInteger value) {
        super(value);
    }

    @Override
    protected BigInteger calcAdd(BigInteger v) {
        return value().add(v);
    }

    @Override
    protected BigInteger calcSubtract(BigInteger v) {
        return value().subtract(v);
    }

    @Override
    protected BigInteger calcMultiply(BigInteger v) {
        return value().multiply(v);
    }

    @Override
    protected BigInteger calcDivide(BigInteger v) throws DivisionByZeroEException {
        checkDivisionByZero(v);
        return value().divide(v);
    }

    @Override
    protected BigInteger calcNegate() {
        return value().negate();
    }

    @Override
    protected BigInteger calcBitCount() {
        return new BigInteger(String.valueOf(value().bitCount()));
    }

    @Override
    protected BigInteger calcMin(BigInteger v) {
        return value().min(v);
    }

    @Override
    protected BigInteger calcMax(BigInteger v) {
        return value().max(v);
    }

    @Override
    public EType<BigInteger> valueOf(BigInteger v) {
        return new BigIntegerEType(v);
    }

    public static EType<BigInteger> parseBigInteger(String v) {
        return new BigIntegerEType(new BigInteger(v));
    }

    @Override
    public BigInteger getZero() {
        return BigInteger.ZERO;
    }

    @Override
    protected int primary() {
        return 2969;
    }
}
