package expression.type;

public class DoubleEType extends AbstractEType<Double> {
    public DoubleEType(Double value) {
        super(value);
    }

    public DoubleEType() {
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
    public EType<Double> valueOf(Double v) {
        return new DoubleEType(v);
    }

    @Override
    public EType<Double> parse(String v) {
        return valueOf(Double.parseDouble(v));
    }
}
