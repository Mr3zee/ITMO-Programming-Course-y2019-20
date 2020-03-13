package expression.parser;

public enum Lexeme {
    ADD("Add", 2), SUB("Subtract", 2), MULT("Multiply", 2), DIV("Divide", 2),
    X("Variable x", 1), Y("Variable y", 1), Z("Variable z", 1),
    OPAR("Opening parenthesis", 1), CPAR("Closing parenthesis", 1), NUM("Number", 1),
    START("Start of the expression", 1), CNT("Count", 1), MIN("Minimum", 2), MAX("Maximum", 2);

    private final String name;
    private final int arity;

    Lexeme(String name, int arity) {
        this.name = name;
        this.arity = arity;
    }

    public String getName() {
        return name;
    }

    public int getArity() {
        return arity;
    }
}
