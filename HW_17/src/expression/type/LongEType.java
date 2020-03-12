package expression.type;

public class LongEType extends AbstractEType<Long> {
    public LongEType(Long value) {
        super(value);
    }

    @Override
    protected Long calcAdd(Long v) {
        return value() + v;
    }

    @Override
    protected Long calcSubtract(Long v) {
        return value() - v;
    }

    @Override
    protected Long calcMultiply(Long v) {
        return value() * v;
    }

    @Override
    protected Long calcDivide(Long v) {
        return value() / v;
    }

    @Override
    protected Long calcNegate() {
        return -value();
    }

    @Override
    protected Long calcBitCount() {
        return (long) Long.bitCount(value());
    }

    @Override
    protected Long calcMin(Long v) {
        return Math.min(value(), v);
    }

    @Override
    protected Long calcMax(Long v) {
        return Math.max(value(), v);
    }

    @Override
    public EType<Long> valueOf(Long v) {
        return new LongEType(v);
    }

    public static EType<Long> parseLong(String v) {
        return new LongEType(Long.parseLong(v));
    }

    @Override
    protected int primary() {
        return 2551;
    }
}
