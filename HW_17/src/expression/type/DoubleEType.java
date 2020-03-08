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
    public EType<Double> valueOf(Double v) {
        return new DoubleEType(v);
    }

    public static EType<Double> parseDouble(String v) {
        return new DoubleEType(Double.parseDouble(v));
    }
}
