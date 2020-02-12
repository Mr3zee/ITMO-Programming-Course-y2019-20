package expression.parser;

public class BaseParser {
    protected ExpressionSource source;
    protected char currentLex;

    protected void nextChar() {
        currentLex = source.hasNext() ? source.next() : '\0';
    }

    protected boolean compare(char expected) {
        if (currentLex == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected boolean compare(String expected) {
        int pos = 0;
        while (pos < expected.length() && compare(expected.charAt(pos++))){
            //skip
        }
        return pos == expected.length();
    }

    protected void expect(char expected) {
        if (expected != currentLex) {
            throw error("Expected \"" + expected + "\", found \"" + currentLex + "\"");
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
