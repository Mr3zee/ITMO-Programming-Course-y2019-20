package expression.parser;

import java.util.Set;

public class BaseParser {
    protected final Set<Character> LEXEMES;
    protected ExpressionSource source;
    private char currentLex;

    public BaseParser(final Set<Character> LEXEMES) {
        this.LEXEMES = LEXEMES;
    }

    protected void nextChar() {
        currentLex = source.hasNext() ? source.next() : source.end();
    }

    protected boolean compare(char expected) {
        if (currentLex == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected FoundNextInfo getNext() {
        String next = nextWord();
        return new FoundNextInfo(next, source.getPosition() - next.length() - 1, source.getExpression());
    }

    protected String nextWord() {
        StringBuilder word = new StringBuilder();
        do {
            word.append(currentLex);
            nextChar();
        } while (!Character.isWhitespace(currentLex) && currentLex != '\0' && (!LEXEMES.contains(currentLex)));
        return word.toString();
    }

    protected String takeNumber() {
        StringBuilder num = new StringBuilder();
        while (Character.isDigit(currentLex)) {
            num.append(currentLex);
            nextChar();
        }
        return num.toString();
    }

    protected boolean testWith(char test, char ... ch) {
        boolean res = false;
        for (char c : ch) {
            res |= test == c;
        }
        return res;
    }

    protected boolean test(char ... ch) {
        return testWith(currentLex, ch);
    }

    protected boolean isDigit() {
        return Character.isDigit(currentLex);
    }

    public char getCurrentLex() {
        return currentLex;
    }

    protected void skipWhitespaces() {
        while (test(' ', '\n', '\r', '\t')) {
            nextChar();
        }
    }

    protected boolean hasNext() {
        return source.hasNext() || currentLex != '\0';
    }

    protected boolean find(String c) {
        if (c.length() == 0) {
            return true;
        }
        return LEXEMES.contains(c.charAt(0));
    }
}
