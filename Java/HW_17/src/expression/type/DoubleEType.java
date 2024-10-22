package expression.type;

public class DoubleEType extends AbstractEType<Double> {
    public DoubleEType(Double value) {
        super(value);
    }

    @Override
    protected Double calcAdd(Double v) {
        return value() + v;
    }

    @Override
    protected Double calcSubtract(Double v) {
        return value() - v;
    }

    @Override
    protected Double calcMultiply(Double v) {
        return value() * v;
    }

    @Override
    protected Double calcDivide(Double v) {
        return value() / v;
    }

    @Override
    protected Double calcNegate() {
        return -value();
    }

    @Override
    protected Double calcBitCount() {
        return (double) Long.bitCount(Double.doubleToLongBits(value()));
    }

    @Override
    protected Double calcMin(Double v) {
        return Math.min(value(), v);
    }

    @Override
    protected Double calcMax(Double v) {
        return Math.max(value(), v);
    }

    @Override
    public EType<Double> valueOf(Double v) {
        return new DoubleEType(v);
    }

    public static EType<Double> parseDouble(String v) {
        return new DoubleEType(Double.parseDouble(v));
    }

    @Override
    protected int primary() {
        return 1637;
    }
}
