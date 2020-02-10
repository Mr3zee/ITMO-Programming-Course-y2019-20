package expression.parser;

import java.util.Map;

import static expression.parser.Lexeme.*;

public class LexemeConverter {
    private static final Map<Character, Lexeme> SINGLE = Map.of(
            '+', PLUS, '-', MINUS, '*', MULT, '/', DIV, '(', OPAR, ')', CPAR, 'x', X, 'y', Y, 'z', Z);
    private static final Map<String, Lexeme> MULTIPLE = Map.of("<<", LSHIFT, ">>", RSHIFT, "abs", ABS, "square", SQR);

    public static Lexeme convert(char c) {
        return SINGLE.getOrDefault(c, INVALID);
    }

    public static Lexeme convert(String c) {
        if (c.length() == 1) {
            return convert(c.charAt(0));
        }
        return MULTIPLE.getOrDefault(c, INVALID);
    }


    public static boolean find(char c) {
        return SINGLE.containsKey(c);
    }

    public static boolean find(String c) {
        if (c.length() == 1) {
            return find(c.charAt(0));
        }
        return MULTIPLE.containsKey(c);
    }
}