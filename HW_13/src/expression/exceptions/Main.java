package expression.exceptions;

import expression.*;
import expression.exceptions.EExceptions.*;

public class Main {
    public static void main(String[] args) {
        String expression = "x ** (y * z)";
        try {
            TripleExpression tripleExpression = new ExpressionParser().parse(expression);
            System.out.println(tripleExpression.toMiniString());
            System.out.println(tripleExpression.evaluate(-10, 1, 1));
        } catch (ExpressionException e) {
            System.out.println(e.getMessage());
        }
    }
}