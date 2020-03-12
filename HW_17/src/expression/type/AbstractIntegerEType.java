package expression.type;

public abstract class AbstractIntegerEType extends AbstractEType<Integer> implements ForbiddenDivisionByZero<Integer> {
    public AbstractIntegerEType(Integer value) {
        super(value);
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
    public Integer getZero() {
        return 0;
    }
}
