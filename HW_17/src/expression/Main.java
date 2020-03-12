package expression;

import expression.parser.ExpressionParser;
import expression.type.ShortEType;

public class Main {
    public static void main(String[] args) {
        String expression = "1 max 2";
        CommonExpression<Short> expression1 = new ExpressionParser<>(ShortEType::parseShort).parse(expression);
        System.out.println(expression1.evaluate(new ShortEType((short) Integer.MIN_VALUE)));
    }
}