import expression.CommonExpression;
import expression.exceptions.ExpressionParser;
import expression.exceptions.EExceptions.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExceptionsTest {
    private ExpressionParser parser;

    @Before
    public void setUp() {
        parser = new ExpressionParser();
        System.out.print("Test of ");
    }

    @After
    public void tearDown() {
        System.out.println("Testing completed\n");
    }

    @Test
    public void ISPEE() {
        System.out.println("IllegalSymbolPEException");
        String exceptionName = "Unknown";
        invalidParse("@ x + y", 0, exceptionName);
        invalidParse("@x + y", 0, exceptionName);
        invalidParse("x@ + y", 1, exceptionName);
        invalidParse("x @ + y", 2, exceptionName);
        invalidParse("x @+ y", 2, exceptionName);
        invalidParse("x +@ y", 3, exceptionName);
        invalidParse("x + @ y", 4, exceptionName);
        invalidParse("x + @y", 4, exceptionName);
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
        System.out.println("MissingLexemePEException");
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
        invalidParse("<<", 0, exceptionName);
        invalidParse("1 <<", 4, exceptionName);
        invalidParse("abs", 3, exceptionName);
        invalidParse("square", 6, exceptionName);
        invalidParse("square abs", 10, exceptionName);
        invalidParse("1 abs 2", 2, exceptionName);
        invalidParse("1abs 2", 1, exceptionName);
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
        invalidParse("x +++ y", 3, exceptionName);
        invalidParse("x +<<< y", 3, exceptionName);
    }

    @Test
    public void NPPEE() {
        System.out.println("NoParenthesisPEException");
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
        System.out.println("EmptyExpressionPEException");
        String exceptionName = "Expression";
        invalidParse("", exceptionName);
        invalidParse(" ", exceptionName);
        invalidParse("  ", exceptionName);
        invalidParse("\n", exceptionName);
        invalidParse("\t", exceptionName);
        invalidParse("\r", exceptionName);
    }

    @Test
    public void IOFPEE() {
        System.out.println("InvalidOperatorFormPEException");
        String exceptionName = "Invalid";
        invalidParse("1 < 2", 2, exceptionName);
        invalidParse("1 > 2", 2, exceptionName);
        invalidParse("1 <) 2", 2, exceptionName);
        invalidParse("1 <> 2", 2, exceptionName);
        invalidParse("1 >< 2", 2, exceptionName);
        invalidParse("1 ><", 2, exceptionName);
        invalidParse("a", 0, exceptionName);
        invalidParse("s", 0, exceptionName);
        invalidParse("sqare", 0, exceptionName);
    }

    @Test
    public void COPEE() {
        System.out.println("ConstantOverflowPEException");
        String exceptionName = "Overflow";
        invalidParse("2147483649", 0, exceptionName);
        invalidParse(" 2147483649", 1, exceptionName);
        invalidParse("x + 2147483649", 4, exceptionName);
    }

    @Test
    public void CUPEE() {
        System.out.println("ConstantUnderflowPEException");
        String exceptionName = "Underflow";
        invalidParse("-2147483649", 0, exceptionName);
        invalidParse(" -2147483649", 1, exceptionName);
        invalidParse("x + -2147483649", 4, exceptionName);
    }

    @Test
    public void DBZEEE() {
        System.out.println("DivisionByZeroEException");
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
        System.out.println("OverflowEEException");
        String exceptionName = "Overflow";
        invalidEvaluate("2147483647 + 1", 0, 0, 0, exceptionName);
        invalidEvaluate("2147483647 + x", 1, 0, 0, exceptionName);
        invalidEvaluate("2147483647 * x", 2, 0, 0, exceptionName);
        invalidEvaluate("-2147483648 / x", -1, 0, 0, exceptionName);
        invalidEvaluate("-2147483648 / x + y", -2, Integer.MAX_VALUE, 0, exceptionName);
        invalidEvaluate("abs x", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, exceptionName);
        invalidEvaluate("square x", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, exceptionName);
        invalidEvaluate("- x", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, exceptionName);
        invalidEvaluate("x - y", Integer.MAX_VALUE, Integer.MIN_VALUE, 0, exceptionName);
    }

    @Test
    public void UEEE() {
        System.out.println("UnderflowEEException");
        String exceptionName = "Underflow";
        invalidEvaluate("x - y", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, exceptionName);
        invalidEvaluate("x * y", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, exceptionName);
    }

    @Test
    public void MWPEE() {
        System.out.println("MissingWhitespacePEException");
        String exceptionName = "Whitespace";
        invalidParse("absa", 3, exceptionName);
        invalidParse("absabs", 3, exceptionName);
        invalidParse("squaresquare", 6, exceptionName);
        invalidParse("squareabs", 6, exceptionName);
    }

    private void validParse(final String input, final String valid) {
        Assert.assertEquals(valid, parse(input).toMiniString());
    }

    private void validEvaluate(final String input, final int valid, int x, int y, int z) {
        Assert.assertEquals(valid, parse(input).evaluate(x, y, z));
    }

    private CommonExpression parse(final String input) {
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
            int found = parse(input).evaluate(x, y, z);
            Assert.fail("Evaluating failure expected for \"" + input + "\", found \"" + found);
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
