package expression;

import expression.parser.ExpressionParser;
import expression.type.BigIntegerEType;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        String expression = "11 * 13";
        CommonExpression<BigInteger> expression1 = new ExpressionParser<>(BigIntegerEType::parseBigInteger).parse(expression);
        System.out.println(expression1.evaluate(new BigIntegerEType(BigInteger.ZERO)));
    }
}