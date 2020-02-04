package expression;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommonExpression expression = new Add(
                new Subtract(
                        new Multiply(
                                new Variable("x"),
                                new Variable("x")
                        ),
                        new Multiply(
                                new Const(2),
                                new Variable("x")
                        )
                ),
                new Const(1)
        );
        String number = scanner.next();
        if (ifDouble(number)) {
            System.out.println(expression.evaluate(Double.parseDouble(number)));
        } else {
            System.out.println(expression.evaluate(Integer.parseInt(number)));
        }
    }

    private static boolean ifDouble(String number) {
        try {
            int n = Integer.parseInt(number);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}