package expression.generic;

import expression.CommonExpression;
import expression.exceptions.*;
import expression.parser.ExpressionParser;
import expression.type.*;

import java.util.Map;
import java.util.function.Function;

public class GenericTabulator implements Tabulator {
    private final Map<String, TableGenerator<? extends Number>> generators =
            Map.of("i", new TableGenerator<>(IntegerEType::parseInteger),
                    "d", new TableGenerator<>(DoubleEType::parseDouble),
                    "bi", new TableGenerator<>(BigIntegerEType::parseBigInteger),
                    "l", new TableGenerator<>(LongEType::parseLong),
                    "s", new TableGenerator<>(ShortEType::parseShort),
                    "u", new TableGenerator<>(UncheckedIntegerEType::parseUncheckedInteger));

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ExpressionException {
        TableGenerator<? extends Number> table = generators.get(mode);
        if (table == null) {
            throw new UnsupportedETypeException(mode);
        }
        return table.generateTable(expression, x1, x2, y1, y2, z1, z2);
    }

    private static class TableGenerator<T extends Number> {
        private final Function<String, EType<T>> parseEType;

        public TableGenerator(Function<String, EType<T>> parseEType) {
            this.parseEType = parseEType;
        }

        private Object[][][] generateTable(String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ExpressionException {
            int dx = x2 - x1 + 1;
            int dy = y2 - y1 + 1;
            int dz = z2 - z1 + 1;
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
                        } catch (EvaluatingExpressionException ignored) {
                        }
                    }
                }
            }
            return result;
        }

    }
}
