package expression;

import expression.parser.ExpressionParser;
import expression.type.IntegerEType;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
//        String expression = "10 20 absd";
//        CommonExpression<Integer> expression1 = new ExpressionParser<>(IntegerEType::parseInteger).parse(expression);
//        System.out.println(expression1.evaluate(new IntegerEType(1)));
        BigInteger bigInteger = new BigInteger("1.0");
        System.out.println(bigInteger);
    }
}