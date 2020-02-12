package expression.parser;

import expression.*;
import expression.exceptions.ExpressionParser;
import expression.exceptions.expExceptions.*;

public class Main {
    public static void main(String[] args) {
        String expression = "1 +++ 2";
        try {
            TripleExpression tripleExpression = new ExpressionParser().parse(expression);
            System.out.println(tripleExpression.toMiniString());
            System.out.println(tripleExpression.evaluate(Integer.MAX_VALUE, 1, 2));
        } catch (ExpressionException e) {
            System.out.println(e.getMessage());
        }
    }
}