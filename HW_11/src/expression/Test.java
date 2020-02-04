package expression;

public class Test {
    public static void main(String[] args) {
        Expression expression = new Multiply(
                new Variable("x"),
                new Subtract(
                        new Const(1),
                        new Variable("x")
                )
        );
        System.out.println(expression.toMiniString());
    }
}
