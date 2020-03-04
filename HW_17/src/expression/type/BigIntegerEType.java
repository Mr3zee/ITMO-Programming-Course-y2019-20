package expression.type;

import java.math.BigInteger;

public class BigIntegerEType extends AbstractEType<BigInteger> {
    public BigIntegerEType(BigInteger value) {
        super(value);
    }

    public BigIntegerEType() {
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
    protected BigInteger calcDivide(BigInteger v) {
        return value().divide(v);
    }

    @Override
    protected BigInteger calcNegate() {
        return value().negate();
    }

    @Override
    public EType<BigInteger> valueOf(BigInteger v) {
        return new BigIntegerEType(v);
    }

    @Override
    public EType<BigInteger> parse(String v) {
        return valueOf(new BigInteger(v));
    }
}
