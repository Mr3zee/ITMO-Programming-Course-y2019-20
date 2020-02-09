package expression.parser;

import java.util.Map;

import static expression.parser.Operations.*;

public class LexemeConvertor {
    private static final Map<Character, Operations> SINGLE = Map.of(
            '+', PLUS, '-', MINUS, '*', MULT, '/', DIV, '(', OPAR, ')', CPAR, 'x', X, 'y', Y, 'z', Z);
    private static final Map<String, Operations> MULTIPLE = Map.of("<<", LSHIFT, ">>", RSHIFT, "abs", ABS, "square", SQR);

    public static Operations convert(char c) {
        return SINGLE.getOrDefault(c, INVALID);
    }

    public static Operations convert(String c) {
        if (c.length() == 1) {
            return convert(c.charAt(0));
        }
        return MULTIPLE.getOrDefault(c, INVALID);
    }
}
