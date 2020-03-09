package expression.generic;

import expression.CommonExpression;
import expression.exceptions.*;
import expression.parser.ExpressionParser;
import expression.type.*;

import java.math.BigInteger;
import java.util.function.Function;

public class GenericTabulator implements Tabulator {
    private String expression;
    private int x1;
    private int dx;
    private int y1;
    private int dy;
    private int z1;
    private int dz;

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ExpressionException {
        initParameters(expression, x1, x2, y1, y2, z1, z2);
        switch (mode) {
            case "i":
                return new TableGenerator<Integer>().tabulate(IntegerEType::parseInteger);
            case "d":
                return new TableGenerator<Double>().tabulate(DoubleEType::parseDouble);
            case "bi":
                return new TableGenerator<BigInteger>().tabulate(BigIntegerEType::parseBigInteger);
            default:
                throw new UnsupportedETypeException(mode);
        }
    }

    private class TableGenerator<T extends Number> {

        private Object[][][] tabulate(Function<String, EType<T>> parseEType) throws ExpressionException {
            Object[][][] result = new Object[dx][dy][dz];
            CommonExpression<T> parsedExpression = new ExpressionParser<>(parseEType).parse(expression);
            for (int i = 0; i < dx; i++) {
                EType<T> xi = parseEType.apply(String.valueOf(x1 + i));
                for (int j = 0; j < dy; j++) {
                    EType<T> yj = parseEType.apply(String.valueOf(y1 + j));
                    for (int k = 0; k < dz; k++) {
                        EType<T> zk = parseEType.apply(String.valueOf(z1 + k));
                        try {
                            result[i][j][k] = parsedExpression.evaluate(xi, yj, zk).value();
                        } catch (EvaluatingExpressionException e) {
                            result[i][j][k] = null;
                        }
                    }
                }
            }
            return result;
        }

    }

    private void initParameters(String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        this.expression = expression;
        this.x1 = x1;
        this.dx = x2 - x1 + 1;
        this.y1 = y1;
        this.dy = y2 - y1 + 1;
        this.z1 = z1;
        this.dz = z2 - z1 + 1;
    }
}
