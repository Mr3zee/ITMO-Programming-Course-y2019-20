package expression;

import expression.parser.ExpressionParser;
import expression.type.IntegerEType;
import expression.type.UncheckedIntegerEType;

public class Main {
    public static void main(String[] args) {
        String expression = "100 * x * y * 100 + z";
        CommonExpression<Integer> expression1 = new ExpressionParser<>(UncheckedIntegerEType::parseUncheckedInteger).parse(expression);
        System.out.println(expression1.evaluate(new UncheckedIntegerEType(2147483641L),
                new UncheckedIntegerEType(21474836411L),
                new UncheckedIntegerEType(2147483631L)));
    }
}