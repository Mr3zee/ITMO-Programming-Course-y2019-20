package expression;

import expression.parser.ExpressionParser;
import expression.type.BigIntegerEType;
import expression.type.DoubleEType;
import expression.type.IntegerEType;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        String expression = "-1 / 0";
        CommonExpression<Double> expression1 = new ExpressionParser<>(DoubleEType::parseDouble).parse(expression);
        System.out.println(expression1.evaluate(new DoubleEType(1.0)));
    }
}