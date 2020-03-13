package expression;

import expression.parser.ExpressionParser;
import expression.type.ShortEType;

public class Main {
    public static void main(String[] args) {
        String expression = "+x  y";
        CommonExpression<Short> expression1 = new ExpressionParser<>(ShortEType::parseShort).parse(expression);
        System.out.println(expression1.evaluate(new ShortEType((short) -3),
                new ShortEType((short) -9),
                new ShortEType((short) -14)));
    }
}