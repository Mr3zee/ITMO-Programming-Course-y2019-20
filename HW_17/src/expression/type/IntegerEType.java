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

    public static EType<Integer> parseInteger(String v) {
        return new IntegerEType(Integer.parseInt(v));
    }
}
