package expression.type;

public class IntegerEType extends AbstractEType<Integer> {
    public IntegerEType(Integer value) {
        super(value);
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
    protected Integer calcDivide(Integer v) {
        return value() / v;
    }

    @Override
    protected Integer calcNegate() {
        return -value();
    }

    @Override
    public EType<Integer> valueOf(Integer v) {
        return new IntegerEType(v);
    }

    @Override
    public EType<Integer> parse(String v) {
        return valueOf(Integer.parseInt(v));
    }
}
