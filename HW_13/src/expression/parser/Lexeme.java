package expression.parser;

public enum Lexeme {
    START("Start of the expression"), NUM("Number"), VAR("Variable"),
    OP("Operator"), OPAR("Opening parenthesis"), CPAR("Closing parenthesis");

    private final String name;

    Lexeme(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
