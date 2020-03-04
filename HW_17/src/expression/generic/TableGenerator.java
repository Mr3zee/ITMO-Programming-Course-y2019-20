package expression.generic;

import expression.CommonExpression;
import expression.exceptions.EExceptions.EvaluatingExpressionException;
import expression.exceptions.ExpressionParser;
import expression.type.EType;

public class TableGenerator<T extends Number> {
    public Object[][][] tabulate(EType<T> type, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        int dx = x2 - x1 + 1;
        int dy = y2 - y1 + 1;
        int dz = z2 - z1 + 1;
        Object[][][] result = new Object[dx][dy][dz];
        CommonExpression<T> parsedExpression = new ExpressionParser<>(type).parse(expression);
        for (int i = 0; i < dx; i++) {
            EType<T> xi = type.parse(String.valueOf(x1 + i));
            for (int j = 0; j < dy; j++) {
                EType<T> yj = type.parse(String.valueOf(y1 + j));
                for (int k = 0; k < dz; k++) {
                    EType<T> zk = type.parse(String.valueOf(z1 + k));
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
