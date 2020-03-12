package expression.type;

public class UncheckedIntegerEType extends IntegerEType {
    public UncheckedIntegerEType(Integer value) {
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
    protected int primary() {
        return 2213;
    }
}
