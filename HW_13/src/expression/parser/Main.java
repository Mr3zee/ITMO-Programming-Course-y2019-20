package expression.parser;

import expression.*;
import expression.exceptions.ExpressionParser;
import expression.exceptions.expExceptions.*;

public class Main {
    public static void main(String[] args) {
//        String expression = "log2-5";
//        try {
//            TripleExpression tripleExpression = new ExpressionParser().parse(expression);
//            System.out.println(tripleExpression.toMiniString());
//            System.out.println(tripleExpression.evaluate(1, 0, 0));
//        } catch (ExpressionException e) {
//            System.out.println(e.getMessage());
//        }
        CommonExpression expression = new Logarithm(new Const(10), new Const(2));
        System.out.println(expression.toMiniString());
        System.out.println(expression.evaluate(1));
        System.out.println(expression.evaluate(1.0));
    }
}