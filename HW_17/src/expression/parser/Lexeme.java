package expression.parser;

public enum Lexeme {
    ADD("Add"), SUB("Subtract"), MULT("Multiply"), DIV("Divide"),
    X("Variable x"), Y("Variable y"), Z("Variable z"),
    OPAR("Opening parenthesis"), CPAR("Closing parenthesis"), NUM("Number"), START("Start of the expression"),
    CNT("Count"), MIN("Minimum"), MAX("Maximum");

    private final String name;

    Lexeme(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
