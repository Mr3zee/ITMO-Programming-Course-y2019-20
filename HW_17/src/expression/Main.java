package expression;

import expression.type.IntegerEType;

public class Main {
    public static void main(String[] args) {
        String expression = "234";
        CommonExpression<Integer> expression1 = new Const<>(new IntegerEType(Integer.parseInt(expression)));
        CommonExpression<Integer> expression2 = new Const<>(new IntegerEType(Integer.parseInt(expression)));
        System.out.println(expression1.equals(expression2));
    }
}