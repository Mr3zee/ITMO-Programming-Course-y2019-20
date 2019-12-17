package expression.parser;

public class BaseParser {
    protected ExpressionSource source;
    protected char current;

    protected void nextChar() {
        current = source.hasNext() ? source.next() : '\0';
    }

    protected boolean compare(char expected) {
        if (current == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected void expect(char expected) {
        if (expected != current) {
            throw error("Expected \"" + expected + "\", found \"" + current + "\"");
        }
        nextChar();
    }

    protected void skipWhitespaces() {
        while (compare(' ') || compare('\n') || compare('\r') || compare('\t')) {
            //skip
        }
    }

    protected ExpressionException error(String message) {
        throw source.error(message);
    }

}
