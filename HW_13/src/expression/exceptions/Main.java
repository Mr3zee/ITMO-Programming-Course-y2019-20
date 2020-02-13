package expression.exceptions;

import expression.*;
import expression.exceptions.EExceptions.*;

public class Main {
    public static void main(String[] args) {
        String expression = "x **** y";
        try {
            TripleExpression tripleExpression = new ExpressionParser().parse(expression);
            System.out.println(tripleExpression.toMiniString());
            System.out.println(tripleExpression.evaluate(Integer.MIN_VALUE, 1, 2));
        } catch (ExpressionException e) {
            System.out.println(e.getMessage());
        }
    }
}