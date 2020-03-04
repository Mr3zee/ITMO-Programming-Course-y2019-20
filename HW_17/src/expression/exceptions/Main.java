package expression.exceptions;

import expression.*;
import expression.type.BigIntegerEType;
import expression.type.IntegerEType;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        String expression = "234";
        CommonExpression<Integer> expression1 = new Const<>(new IntegerEType(Integer.parseInt(expression)));
        CommonExpression<BigInteger> expression2 = new Const<>(new BigIntegerEType(new BigInteger(expression)));
        System.out.println(expression1.equals(expression2));
    }
}