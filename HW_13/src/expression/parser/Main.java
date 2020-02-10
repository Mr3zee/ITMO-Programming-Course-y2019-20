package expression.parser;

import expression.*;
import expression.exceptions.ExpressionParser;
import expression.exceptions.expExceptions.*;

public class Main {
    public static void main(String[] args) {
//        String expression = "1a";
//        try {
//            TripleExpression tripleExpression = new ExpressionParser().parse(expression);
//            System.out.println(tripleExpression.toMiniString());
//            System.out.println(tripleExpression.evaluate(Integer.MIN_VALUE, Integer.MAX_VALUE, 0));
//        } catch (ExpressionException e) {
//            System.out.println(e.getMessage());
//        }
        System.out.println(new Pow2(new Const(-1)).evaluate(0));
        System.out.println(new Pow2(new Const(-1)).evaluate(0.0));
        System.out.println(new Pow2(new Variable("x")).toMiniString());
    }
}