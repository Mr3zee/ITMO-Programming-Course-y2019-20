package expression.parser;

import java.util.Map;
import java.util.Set;

public class BaseParser {
    protected final Set<Character> LEXEMES;
    protected final Map<Character, String> WORDS;
    protected ExpressionSource source;
    private char currentLex;

    public BaseParser(Set<Character> LEXEMES, Map<Character, String> WORDS) {
        this.LEXEMES = LEXEMES;
        this.WORDS = WORDS;
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
        String next;
        next = takeWord(WORDS.get(currentLex));
        next = next == null ? nextWord() : next;
        return new FoundNextInfo(next, source.getPosition() - next.length() - 1, source.getExpression());
    }

    protected String takeWord(String expect) {
        if (expect == null) {
            return null;
        }
        StringBuilder word = new StringBuilder();
        int i = 0;
        while (i < expect.length() && currentLex == expect.charAt(i++)) {
            word.append(getCurrentLex());
            nextChar();
        }
        return word.toString();
    }

    protected String nextWord() {
        StringBuilder word = new StringBuilder();
        char first = currentLex;
        while (!Character.isWhitespace(currentLex) && currentLex != '\0' && (!LEXEMES.contains(currentLex) || currentLex == first)) {
            word.append(currentLex);
            nextChar();
        }
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
        while (compare(' ') || compare('\n') || compare('\r') || compare('\t')) {
            //skip
        }
    }

    protected boolean hasNext() {
        return source.hasNext() || currentLex != '\0';
    }

}
