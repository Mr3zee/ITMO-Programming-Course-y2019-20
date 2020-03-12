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

    protected boolean compareAndGetNext(char expected) {
        if (currentLex == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected NextWordParameters getNext() {
        String next = nextWord();
        return getNext(next, source.getPosition() - next.length() - 1);
    }

    protected NextWordParameters getNext(String word, int position) {
        return new NextWordParameters(word, position, source.getExpression());
    }

    protected String nextWord() {
        StringBuilder word = new StringBuilder();
        do {
            word.append(currentLex);
            nextChar();
        } while (!Character.isWhitespace(currentLex)/* && currentLex != '\0'*/ && (!LEXEMES.contains(currentLex)));
        return word.toString();
    }

    protected String takeNumber() {
        StringBuilder num = new StringBuilder();
        while (Character.isDigit(currentLex) || currentLex == '.') {
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

    protected boolean compare(char ... ch) {
        return testWith(currentLex, ch);
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
        while (compare(' ', '\n', '\r', '\t')) {
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
}
