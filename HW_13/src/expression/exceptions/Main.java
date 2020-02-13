package expression.exceptions;

import expression.*;
import expression.exceptions.EExceptions.*;

public class Main {
    public static void main(String[] args) {
        String expression = "1 << 2 + 3 * 4 ** 5";
        try {
            TripleExpression tripleExpression = new ExpressionParser().parse(expression);
            System.out.println(tripleExpression.toMiniString());
            System.out.println(tripleExpression.evaluate(1024, 3, 2));
        } catch (ExpressionException e) {
            System.out.println(e.getMessage());
        }
    }
}