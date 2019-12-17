package expression.parser;

import expression.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TripleExpression tripleExpression = new ExpressionParser().parse("abs(x + y)");
        System.out.println(tripleExpression);
        System.out.println(tripleExpression.evaluate(0, 0, 1));
    }
}