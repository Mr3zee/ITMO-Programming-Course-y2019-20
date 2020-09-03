package expression.parser_tools;

public enum Lexeme {
    PLUS("Add"), MINUS("Subtract"), MULT("Multiply"), DIV("Divide"),
    LSHIFT("Left shift"), RSHIFT("Right shift"), ABS("Abs"), SQR("Square"),
    X("Variable x"), Y("Variable y"), Z("Variable z"),
    OPAR("Opening parenthesis"), CPAR("Closing parenthesis"), NUM("Number"), START("Start of the expression"),
    POW2("Pow2"), LOG2("Log2"), POW("Power"), LOG("Logarithm"),
    REV("Reverse"), DIG("Digits");

    private final String name;

    Lexeme(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
