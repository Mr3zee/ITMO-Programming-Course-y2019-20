package expression.parser;

public enum Priority {
    END(-1), PAR(0), SHIFT(5), ADD(10), SUB(10), MUL(20), DIV(20), MINUS(100), ABS(100), SQUARE(100);
    /*
    PAR has priority of 0 due to realisation of method ExpressionParser.nextHasGreaterPriority() :
        compare(')') is the same as next has priority PAR
    */

    final int priority;

    Priority(final int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
