package expression.parser;

import expression.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TripleExpression tripleExpression = new ExpressionParser().parse("x + y + 1 + 2 + 3");
        System.out.println(tripleExpression);
        System.out.println(tripleExpression.evaluate(1, 0, -10));
    }
}