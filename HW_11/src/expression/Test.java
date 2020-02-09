package expression;

public class Test {
    public static void main(String[] args) {
        Expression expression = new Multiply(
                new Variable("x"),
                new Add(
                    new Variable("x"),
                    new Const(-1)
                )
        );
        System.out.println(expression.toMiniString());
    }
}
