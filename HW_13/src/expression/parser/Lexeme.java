package expression.parser;

public enum Lexeme {
    PLUS("Add"), MINUS("Subtract"), MULT("Multiply"), DIV("Divide"),
    LSHIFT("Left shift"), RSHIFT("Right shift"), ABS("Abs"), SQR("Square"),
    INVALID("Invalid lexeme"), X("Variable x"), Y("Variable y"), Z("Variable z"),
    OPAR("Opening parenthesis"), CPAR("Closing parenthesis"), NUM("Number"), START("Start of the expression");

    private final String name;

    Lexeme(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
