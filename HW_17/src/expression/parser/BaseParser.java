package expression.parser;

import java.util.Map;
import java.util.Set;

public class BaseParser {
    protected final Set<Character> LEXEMES;
    protected final Map<String, Lexeme> WORDS;
    protected ExpressionSource source;
    private char currentLex;

    public BaseParser(Set<Character> LEXEMES, Map<String, Lexeme> WORDS) {
        this.LEXEMES = LEXEMES;
        this.WORDS = WORDS;
    }

    protected void nextChar() {
        currentLex = source.hasNext() ? source.next() : source.end();
    }

    protected String takeWord() {
        StringBuilder word = new StringBuilder();
        do {
            word.append(currentLex);
            nextChar();
        } while (!Character.isWhitespace(currentLex) && (!LEXEMES.contains(currentLex)));
        return word.toString();
    }

    protected ExceptionParameters getExceptionParameters() {
        String next = takeWord();
        return getExceptionParameters(next, source.getPosition() - next.length() - 1);
    }

    protected ExceptionParameters getExceptionParameters(String word, int position) {
        return new ExceptionParameters(word, position, source.getExpression());
    }

    protected String takeNumber() {
        StringBuilder num = new StringBuilder();
        while (Character.isDigit(currentLex) || currentLex == '.') {
            num.append(currentLex);
            nextChar();
        }
        return num.toString();
    }

    protected boolean compareAndSkip(String expected) {
        if (getSequence(expected.length()).equals(expected)) {
            return true;
        }
        rollBack(expected.length());
        return false;
    }

    protected boolean compare(String ... ch) {
        for (String c : ch) {
            String next = getSequence(c.length());
            rollBack(c.length());
            if (next.equals(c)) {
                return true;
            }
        }
        return false;
    }

    private String getSequence(int length) {
        StringBuilder next = new StringBuilder();
        for (int i = 0; i < length; i++) {
            next.append(currentLex);
            nextChar();
        }
        return next.toString();
    }

    protected boolean isDigit() {
        return Character.isDigit(currentLex);
    }

    protected boolean checkDouble(String number) {
        for (char c: number.toCharArray()) {
            if (c == '.') {
                return true;
            }
        }
        return false;
    }

    public char getCurrentLex() {
        return currentLex;
    }

    protected void skipWhitespaces() {
        while (Character.isWhitespace(currentLex)) {
            nextChar();
        }
    }

    protected boolean hasNext() {
        return source.hasNext() || currentLex != '\0';
    }

    protected boolean findLexeme(String c) {
        if (c.length() == 0) {
            return true;
            // end of the expression
        }
        if (c.length() == 1) {
            return LEXEMES.contains(c.charAt(0));
        }
        return WORDS.containsKey(c);
    }

    protected void rollBack(int v) {
        source.rollBack(v + 1);
        nextChar();
    }
}
