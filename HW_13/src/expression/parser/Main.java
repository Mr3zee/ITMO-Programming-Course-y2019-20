package expression.parser;

import expression.*;
import expression.exceptions.ExpressionParser;
import expression.exceptions.expExceptions.*;

public class Main {
    public static void main(String[] args) {
//        String expression = "(((z) * (x))) - (((378182610) * (x)) */ (((y) / (-773879020))))";
//        try {
//            TripleExpression tripleExpression = new ExpressionParser().parse(expression);
//            System.out.println(tripleExpression.toMiniString());
//            System.out.println(tripleExpression.evaluate(0, 1, 2));
//        } catch (ExpressionException e) {
//            System.out.println(e.getMessage());
//        }
        CommonExpression expression = new Reverse(new Const(-10));
        System.out.println(expression.toMiniString());
        System.out.println(expression.evaluate(0));
        System.out.println(expression.evaluate(0.0));
    }
}