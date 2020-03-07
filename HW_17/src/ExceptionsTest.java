import expression.CommonExpression;
import expression.exceptions.EvaluatingExpressionException;
import expression.exceptions.ParsingExpressionException;
import expression.parser.ExpressionParser;
import expression.type.EType;
import expression.type.IntegerEType;
import org.junit.*;

import java.util.function.Function;

public class ExceptionsTest {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private ExpressionParser<Integer> parser;
    private Function<String, EType<Integer>> parseEType;

    @Before
    public void setUp() {
        parseEType = IntegerEType::parseInteger;
        parser = new ExpressionParser<>(parseEType);
        System.out.print(ANSI_CYAN + "Test of ");
    }

    @After
    public void tearDown() {
        System.out.println(ANSI_YELLOW + "Testing completed\n" + ANSI_RESET);
    }

    @Test
    public void ISPEE() {
        System.out.println("IllegalSymbolPEException" + ANSI_RESET);
        String exceptionName = "Unknown";
        invalidParse("@ x + y", 0, exceptionName);
        invalidParse("@x + y", 0, exceptionName);
        invalidParse("x@ + y", 1, exceptionName);
        invalidParse("x @ + y", 2, exceptionName);
        invalidParse("x @+ y", 2, exceptionName);
        invalidParse("x +@ y", 3, exceptionName);
        invalidParse("x + @ y", 4, exceptionName);
        invalidParse("x + @y", 4, exceptionName);
        invalidParse("x + @@@@@@@y", 4, exceptionName);
        invalidParse("x + y@", 5, exceptionName);
        invalidParse("x + y       @", 12, exceptionName);
        invalidParse("1a", 1, exceptionName);
        invalidParse("1a2", 1, exceptionName);
        invalidParse("-1a", 2, exceptionName);
        invalidParse("1 ab 2", 2, exceptionName);
        invalidParse("1ab 2", 1, exceptionName);
    }

    @Test
    public void MLPEE() {
        System.out.println("MissingLexemePEException" + ANSI_RESET);
        String exceptionName = "Missing";
        invalidParse("x y", 2, exceptionName);
        invalidParse("x  y", 3, exceptionName);
        invalidParse("+x  y", 0, exceptionName);
        invalidParse("+", 0, exceptionName);
        invalidParse("-", 1, exceptionName);
        invalidParse("x ++ y", 3, exceptionName);
        invalidParse("x + + y", 4, exceptionName);
        invalidParse("x - - +y", 6, exceptionName);
        invalidParse("1 1", 2, exceptionName);
        invalidParse("1   1", 4, exceptionName);
        invalidParse("* 1   1", 0, exceptionName);
        invalidParse(" 1 * /  1", 5, exceptionName);
        invalidParse("(1 + 2) (3 + 4)", 8, exceptionName);
        invalidParse("(1 + 2)(3 + 4)", 7, exceptionName);
        invalidParse("(1 + 2)(", 7, exceptionName);
        invalidParse("(1 + 2) xyz", 8, exceptionName);
        invalidParse("(1 + 2)xyz", 7, exceptionName);
        invalidParse("xyz (1 + 2)", 1, exceptionName);
        invalidParse("y(1 + 2)", 1, exceptionName);
        invalidParse("z (1 + 2)", 2, exceptionName);
        invalidParse("()", 1, exceptionName);
        invalidParse("(())", 2, exceptionName);
    }

    @Test
    public void NPPEE() {
        System.out.println("NoParenthesisPEException" + ANSI_RESET);
        String exceptionName = "Expected";
        invalidParse("x + y)", 5, exceptionName);
        invalidParse("(x + y", 6, exceptionName);
        invalidParse("(x + y))", 7, exceptionName);
        invalidParse("x + (y", 6, exceptionName);
        invalidParse("x) + y", 1, exceptionName);
        invalidParse("((x) + y))", 9, exceptionName);
        invalidParse("((((((((((x)))))))))))", 21, exceptionName);
    }

    @Test
    public void EEPEE() {
        System.out.println("EmptyExpressionPEException" + ANSI_RESET);
        String exceptionName = "Expression";
        invalidParse("", exceptionName);
        invalidParse(" ", exceptionName);
        invalidParse("  ", exceptionName);
        invalidParse("\n", exceptionName);
        invalidParse("\t", exceptionName);
        invalidParse("\r", exceptionName);
    }

    @Test
    public void COPEE() {
        System.out.println("ConstantOverflowPEException" + ANSI_RESET);
        String exceptionName = "Overflow";
        invalidParse("2147483649", 0, exceptionName);
        invalidParse(" 2147483649", 1, exceptionName);
        invalidParse("x + 111111111111111111111111111111", 4, exceptionName);
    }

    @Test
    public void CUPEE() {
        System.out.println("ConstantUnderflowPEException" + ANSI_RESET);
        String exceptionName = "Underflow";
        invalidParse("-2147483649", 0, exceptionName);
        invalidParse(" -2147483649", 1, exceptionName);
        invalidParse("x + -2147483649", 4, exceptionName);
        invalidParse("x + -1111111111111111111111111", 4, exceptionName);
    }

    @Test
    public void DBZEEE() {
        System.out.println("DivisionByZeroEException" + ANSI_RESET);
        String exceptionName = "Division";
        invalidEvaluate("x / 0", 0, 1, 1, exceptionName);
        invalidEvaluate("x / 0", 1, 1, 1, exceptionName);
        invalidEvaluate("x / (y - z)", 1, 1, 1, exceptionName);
        invalidEvaluate("x / y", 1, 0, 1, exceptionName);
        invalidEvaluate("x / z", 1, 1, 0, exceptionName);
        invalidEvaluate("0 / 0", 1, 1, 1, exceptionName);
    }

    @Test
    public void OEEE() {
        System.out.println("OverflowEEException" + ANSI_RESET);
        String exceptionName = "Overflow";
        invalidEvaluate("2147483647 + 1", 0, 0, 0, exceptionName);
        invalidEvaluate("2147483647 + x", 1, 0, 0, exceptionName);
        invalidEvaluate("2147483647 * x", 2, 0, 0, exceptionName);
        invalidEvaluate("-2147483648 / x", -1, 0, 0, exceptionName);
        invalidEvaluate("-2147483648 / x + y", -2, Integer.MAX_VALUE, 0, exceptionName);
    }

    @Test
    public void UEEE() {
        System.out.println("UnderflowEEException" + ANSI_RESET);
        String exceptionName = "Underflow";
        invalidEvaluate("x - y", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, exceptionName);
        invalidEvaluate("x * y", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, exceptionName);
    }

    @Test
    public void plusMinusTest() {
        System.out.println("Plus and Minus parse" + ANSI_RESET);
        validParse("x + y", "x + y");
        validEvaluate(2, "x + y", 1, 1, 1);
        validParse("x + z", "x + z");
        validEvaluate(2, "x + z", 1, 1, 1);
        validParse("123 + 23", "123 + 23");
        validEvaluate(146, "123 + 23", 1, 1, 1);
        validParse("x - z", "x - z");
        validEvaluate(0, "x - z", 1, 1, 1);
        validParse("z - y", "z - y");
        validEvaluate(-11, "z - y", 1, 13, 2);
        validParse("6 - 10", "6 - 10");
        validEvaluate(-4, "6 - 10", 1, 13, 2);
        validParse("z - y - 1", "z - y - 1");
        validEvaluate(-12, "z - y - 1", 1, 13, 2);
        validParse("z + y + 1", "z + y + 1");
        validEvaluate(16, "z + y + 1", 1, 13, 2);
    }

    @Test
    public void multiplyDivideTest() {
        System.out.println("Multiply and Divide parse" + ANSI_RESET);
        validParse("x * y", "x * y");
        validEvaluate(13, "x * y", 1, 13, 2);
        validParse("x * z", "x * z");
        validEvaluate(2, "x * z", 1, 13, 2);
        validParse("123 * 23", "123 * 23");
        validEvaluate(2829, "123 * 23", 1, 13, 2);
        validParse("x / z", "x / z");
        validEvaluate(0, "x / z", 1, 13, 2);
        validParse("z / y", "z / y");
        validEvaluate(1, "z / y", 1, 13, 22);
        validParse("6 / 10", "6 / 10");
        validEvaluate(0, "6 / 10", 1, 13, 22);
        validParse("z / y / 1", "z / y / 1");
        validEvaluate(2, "z / y / 1", 1, 13, 26);
        validParse("z * y * 1", "z * y * 1");
        validEvaluate(338, "z * y * 1", 1, 13, 26);
    }

    @Test
    public void whitespacesTest() {
        System.out.println("Whitespaces parse" + ANSI_RESET);
        validParse("x + y", "x+y");
        validParse("x + y", "x    +y");
        validParse("x + y", "x    +       y");
        validParse("x + y", "x+       y");
        validParse("x + y", "x\t\n \t\r+\n\n\n\n\n\n\n\r\r\r\r       y");
    }

    @Test
    public void parenthesisTest() {
        System.out.println("Parenthesis parse" + ANSI_RESET);
        validParse("x", "(x)");
        validParse("x", "(((((x)))))");
        validParse("x - y", "((((((x))))) - (y))");
        validParse("x - (y - z)", "((((((x))))) - ((y) - z))");
        validParse("x - (y - (1 - (2 - (3 - 4))))", "(x - (y - (1 - (2 - ( 3 - 4)))))");
        validParse("11234567", "(             11234567\n)");
    }

    @Test
    public void unaryTest() {
        System.out.println("Unary parse" + ANSI_RESET);
        validParse("-x", "(-x)");
        validEvaluate(1024, "(-x)", -1024, 1, 32);
        validParse("-x", "-(x)");
        validEvaluate(1024, "-(x)", -1024, 1, 32);
        validParse("-(x - y)", "-(x - y)");
        validEvaluate(1025, "-(x - y)", -1024, 1, 32);
        validParse("-(x - y)", "-      (x - y)");
        validEvaluate(1025, "-      (x - y)", -1024, 1, 32);
    }

    @Test
    public void randomTests() {
        System.out.println("Random parser tests" + ANSI_RESET);
        validParse("1 * 2 - 3", "1 * 2 - 3");
        validEvaluate(-1, "1 * 2 - 3", 123, 1, 32);
        validParse("1 * 2 - 3", "(1 * 2) - 3");
        validEvaluate(-1, "(1 * 2) - 3", 123, 1, 32);
        validParse("1 * (2 - 4)", "1 * (2 - 4)");
        validEvaluate(-2, "1 * (2 - 4)", 123, 1, 32);
    }

    private void validParse(final String valid, final String input) {
        Assert.assertEquals(valid, parse(input).toMiniString());
        System.out.println("\"" + input + "\" - " + ANSI_YELLOW + "Passed!" + ANSI_RESET);
    }

    private void validEvaluate(final Integer valid, final String input, int x, int y, int z) {
        EType<Integer> xt = parseEType.apply(String.valueOf(x));
        EType<Integer> yt = parseEType.apply(String.valueOf(y));
        EType<Integer> zt = parseEType.apply(String.valueOf(z));
        Assert.assertEquals(valid, parse(input).evaluate(xt, yt, zt).value());
        System.out.println("\"" + input + " = " + valid + "\", with args: (" + x + ", " + y + ", " + z + ") - "  + ANSI_YELLOW + "Passed!" + ANSI_RESET);
    }

    private CommonExpression<Integer> parse(final String input) {
        return parser.parse(input);
    }

    private void invalidParse(final String input, final int pos, final String name) {
        try {
            fail(input);
        } catch (ParsingExpressionException e) {
            Assert.assertEquals(pos, getPosition(e.getMessage()));
            Assert.assertEquals(name, getName(e.getMessage()));
            System.out.println(e.getMessage());
        }
    }

    private void invalidParse(final String input, final String name) {
        try {
            fail(input);
        } catch (ParsingExpressionException e) {
            Assert.assertEquals(name, getName(e.getMessage()));
            System.out.println(e.getMessage());
        }
    }

    private void fail(final String input) {
        String found = parse(input).toMiniString();
        Assert.fail("Parsing failure expected for \"" + input + "\", found \"" + found);
    }

    private void invalidEvaluate(final String input, final int x, final int y, final int z, final String name) {
        try {
            EType<Integer> xt = parseEType.apply(String.valueOf(x));
            EType<Integer> yt = parseEType.apply(String.valueOf(y));
            EType<Integer> zt = parseEType.apply(String.valueOf(z));
            Integer found = parse(input).evaluate(xt, yt, zt).value();
            Assert.fail("Evaluating failure expected for \"" + input + "\", found \"" + found + "\"");
        } catch (EvaluatingExpressionException e) {
            Assert.assertEquals(name, getName(e.getMessage()));
            System.out.println(e.getMessage());
        }
    }

    private int getPosition(final String message) {
        StringBuilder pos = new StringBuilder();
        int i = message.length() - 2;
        while (message.charAt(i--) != '\"') {}
        i -= 4;
        while (!Character.isWhitespace(message.charAt(i))) {
            pos.append(message.charAt(i--));
        }
        return Integer.parseInt(pos.reverse().toString());
    }

    private String getName(final String message) {
        int i = 0;
        while (!Character.isWhitespace(message.charAt(i++))) {}
        int j = i;
        while (!Character.isWhitespace(message.charAt(i++))) {}
        return message.substring(j, i - 1);
    }
}
