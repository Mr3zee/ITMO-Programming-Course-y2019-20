import expression.CommonExpression;
import expression.exceptions.ExpressionParser;
import expression.exceptions.EExceptions.*;
import org.junit.*;

@SuppressWarnings("ALL")
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

    private ExpressionParser parser;

    @Before
    public void setUp() {
        parser = new ExpressionParser();
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
        invalidParse("x + ** y", 4, exceptionName);
        invalidParse("x + // y", 4, exceptionName);
        invalidParse("x +// y", 3, exceptionName);
        invalidParse("x +** y", 3, exceptionName);
        invalidParse("x **", 4, exceptionName);
        invalidParse("x //", 4, exceptionName);
        invalidParse("x // // y", 5, exceptionName);
        invalidParse("x ** ** y", 5, exceptionName);
        invalidParse("x **** y", 4, exceptionName);
        invalidParse("x //// y", 4, exceptionName);
        invalidParse("x / // y", 4, exceptionName);
        invalidParse("x * // y", 4, exceptionName);
        invalidParse("x * ** y", 4, exceptionName);
        invalidParse("x / ** y", 4, exceptionName);
        invalidParse("x // * y", 5, exceptionName);
        invalidParse("x // / y", 5, exceptionName);
        invalidParse("x ** / y", 5, exceptionName);
        invalidParse("x ** * y", 5, exceptionName);
        invalidParse("** x + y", 0, exceptionName);
        invalidParse("// x - y", 0, exceptionName);
        invalidParse("x + pow2 * y", 9, exceptionName);
        invalidParse("x  / log2 * y", 10, exceptionName);
        invalidParse("x log2 2 * y", 2, exceptionName);
        invalidParse("x pow2 2 * y", 2, exceptionName);
        invalidParse("x + log2", 8, exceptionName);
        invalidParse("x + pow2", 8, exceptionName);
        invalidParse("log2", 4, exceptionName);
        invalidParse("pow2", 4, exceptionName);
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
    public void IOFPEE() {
        System.out.println("InvalidOperatorFormPEException" + ANSI_RESET);
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
        invalidParse("ab 1", 0, exceptionName);
        invalidParse("pow3 1", 0, exceptionName);
        invalidParse("po2 1", 0, exceptionName);
        invalidParse("lo2 1", 0, exceptionName);
        invalidParse("log3 1", 0, exceptionName);
        invalidParse("lg2 1", 0, exceptionName);
        invalidParse("pw2 1", 0, exceptionName);
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
        invalidEvaluate("abs x", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, exceptionName);
        invalidEvaluate("square x", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, exceptionName);
        invalidEvaluate("square x", Integer.MAX_VALUE / 2 + 1, Integer.MAX_VALUE, 0, exceptionName);
        invalidEvaluate("reverse x", Integer.MAX_VALUE / 2 + 1, Integer.MAX_VALUE, 0, exceptionName);
        invalidEvaluate("- x", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, exceptionName);
        invalidEvaluate("x - y", Integer.MAX_VALUE, Integer.MIN_VALUE, 0, exceptionName);
        invalidEvaluate("2 ** 31", 0, 0, 0, exceptionName);
        invalidEvaluate("2 ** 32", 0, 0, 0, exceptionName);
        invalidEvaluate("22 ** 32", 0, 0, 0, exceptionName);
        invalidEvaluate("222 ** 32", 0, 0, 0, exceptionName);
        invalidEvaluate("2222 ** 32", 0, 0, 0, exceptionName);
        invalidEvaluate("22222 ** 32", 0, 0, 0, exceptionName);
        invalidEvaluate("222222 ** 32", 0, 0, 0, exceptionName);
        invalidEvaluate("2222222 ** 32", 0, 0, 0, exceptionName);
        invalidEvaluate("22222222 ** 32", 0, 0, 0, exceptionName);
        invalidEvaluate("x ** 32", Integer.MAX_VALUE, 0, 0, exceptionName);
        invalidEvaluate("x ** y", Integer.MAX_VALUE, Integer.MAX_VALUE, 0, exceptionName);
        invalidEvaluate("x ** 2", Integer.MAX_VALUE, 0, 0, exceptionName);
        invalidEvaluate("x ** 2", Integer.MIN_VALUE, 0, 0, exceptionName);
        invalidEvaluate("x ** 22", Integer.MIN_VALUE, 0, 0, exceptionName);
        invalidEvaluate("x ** 4", Integer.MIN_VALUE, 0, 0, exceptionName);
        invalidEvaluate("x ** 4", Integer.MIN_VALUE, 0, 0, exceptionName);
    }

    @Test
    public void UEEE() {
        System.out.println("UnderflowEEException" + ANSI_RESET);
        String exceptionName = "Underflow";
        invalidEvaluate("x - y", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, exceptionName);
        invalidEvaluate("x * y", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, exceptionName);
        invalidEvaluate("reverse x", -(Integer.MAX_VALUE / 2), 0, 0, exceptionName);
        invalidEvaluate("-3 ** 31", 0, 0, 0, exceptionName);
        invalidEvaluate("-22 ** 33", 0, 0, 0, exceptionName);
        invalidEvaluate("-222 ** 33", 0, 0, 0, exceptionName);
        invalidEvaluate("-2222 ** 33", 0, 0, 0, exceptionName);
        invalidEvaluate("-22222 ** 33", 0, 0, 0, exceptionName);
        invalidEvaluate("-222222 ** 33", 0, 0, 0, exceptionName);
        invalidEvaluate("-2222222 ** 33", 0, 0, 0, exceptionName);
        invalidEvaluate("-22222222 ** 33", 0, 0, 0, exceptionName);
        invalidEvaluate("-222222222 ** 33", 0, 0, 0, exceptionName);
        invalidEvaluate("-x ** 33", Integer.MAX_VALUE, 0, 0, exceptionName);
        invalidEvaluate("x ** 33", Integer.MIN_VALUE, 0, 0, exceptionName);
        invalidEvaluate("x ** 3", Integer.MIN_VALUE, 0, 0, exceptionName);
        invalidEvaluate("x ** 333", Integer.MIN_VALUE, 0, 0, exceptionName);
    }

    @Test
    public void MWPEE() {
        System.out.println("MissingWhitespacePEException" + ANSI_RESET);
        String exceptionName = "Whitespace";
        invalidParse("absa", 3, exceptionName);
        invalidParse("abs1", 3, exceptionName);
        invalidParse("absz", 3, exceptionName);
        invalidParse("absabs", 3, exceptionName);
        invalidParse("squaresquare", 6, exceptionName);
        invalidParse("squareabs", 6, exceptionName);
        invalidParse("square1", 6, exceptionName);
        invalidParse("squarex", 6, exceptionName);
        invalidParse("log22", 4, exceptionName);
        invalidParse("log2x", 4, exceptionName);
        invalidParse("log2y", 4, exceptionName);
        invalidParse("log2z", 4, exceptionName);
        invalidParse("log2*", 4, exceptionName);
        invalidParse("log2+", 4, exceptionName);
        invalidParse("log2/", 4, exceptionName);
        invalidParse("log2<<", 4, exceptionName);
        invalidParse("log2>>", 4, exceptionName);
        invalidParse("log20", 4, exceptionName);
        invalidParse("pow20", 4, exceptionName);
        invalidParse("pow2x", 4, exceptionName);
        invalidParse("pow2y", 4, exceptionName);
        invalidParse("pow2z", 4, exceptionName);
        invalidParse("pow2+", 4, exceptionName);
        invalidParse("pow2abs2", 4, exceptionName);
    }

    @Test
    public void IFPEEE() {
        System.out.println("InvalidFunctionParametersEEException" + ANSI_RESET);
        String exceptionName = "Parameter(s)";
        invalidEvaluate("log2-1", 0, 0, 0, exceptionName);
        invalidEvaluate("log2 -1", 0, 0, 0, exceptionName);
        invalidEvaluate("log2 0", 0, 0, 0, exceptionName);
        invalidEvaluate("pow2-1", 0, 0, 0, exceptionName);
        invalidEvaluate("pow2 -1", 0, 0, 0, exceptionName);
        invalidEvaluate("10 // -1", 0, 0, 0, exceptionName);
        invalidEvaluate("10 // 0", 0, 0, 0, exceptionName);
        invalidEvaluate("0 // 0", 0, 0, 0, exceptionName);
        invalidEvaluate("0 // 10", 0, 0, 0, exceptionName);
        invalidEvaluate("-1 // 10", 0, 0, 0, exceptionName);
        invalidEvaluate("0 ** 0", 0, 0, 0, exceptionName);
        invalidEvaluate("0 ** -1", 0, 0, 0, exceptionName);
        invalidEvaluate("10 ** -1", 0, 0, 0, exceptionName);
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
    public void shiftsTest() {
        System.out.println("Shifts parse" + ANSI_RESET);
        validParse("x << y", "x << y");
        validEvaluate(8192, "x << y", 1, 13, 26);
        validParse("x << z", "x << z");
        validEvaluate(67108864, "x << z", 1, 13, 26);
        validParse("123 << 23", "123 << 23");
        validEvaluate(1031798784, "123 << 23", 1, 13, 26);
        validParse("x >> z", "x >> z");
        validEvaluate(32, "x >> z", 1024, 13, 5);
        validParse("z >> y", "z >> y");
        validEvaluate(0, "z >> y", 1024, 13, 5);
        validParse("z >> y", "z >> y");
        validEvaluate(-1, "z >> y", 1024, 100, -5);
        validParse("6 >> 10", "6 >> 10");
        validEvaluate(0, "6 >> 10", 1024, 100, -5);
        validParse("z >> y >> 1", "z >> y >> 1");
        validEvaluate(8, "z >> y >> 1", 1024, 1, 32);
        validParse("z << y << 1", "z << y << 1");
        validEvaluate(128, "z << y << 1", 1024, 1, 32);
    }

    @Test
    public void powerLogarithmTest() {
        System.out.println("Power and Logarithm parse" + ANSI_RESET);
        validParse("x ** y", "x ** y");
        validEvaluate(1024, "x ** y", 1024, 1, 32);
        validParse("x ** z", "x ** z");
        validEvaluate((int) Math.pow(2, 30), "x ** z", 1024, 1, 3);
        validParse("4 ** 5", "4 ** 5");
        validEvaluate((int) Math.pow(4, 5), "4 ** 5", 1024, 1, 3);
        validParse("x // z", "x // z");
        validEvaluate((int) (Math.log(1024) / Math.log(3)), "x // z", 1024, 1, 3);
        validParse("z // y", "z // y");
        validEvaluate((int) (Math.log(3) / Math.log(2)), "z // y", 1024, 2, 3);
        validParse("6 // 10", "6 // 10");
        validEvaluate((int) (Math.log(6) / Math.log(10)), "6 // 10", 1024, 2, 3);
        validParse("z // y // 2", "z // y // 2");
        validEvaluate(2, "z // y // 2", 1024, 2, 35);
        validParse("z ** y ** 2", "z ** y ** 2");
        validEvaluate(81, "z ** y ** 2", 1024, 2, 3);
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
    public void parethesisTest() {
        System.out.println("Parethsis parse" + ANSI_RESET);
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
        validParse("abs 1", "abs 1");
        validEvaluate(1, "abs 1", -1024, 1, 32);
        validParse("abs 1", "abs     1");
        validEvaluate(1, "abs     1", 1, 1, 32);
        validParse("abs x", "abs   \nx");
        validEvaluate(1, "abs   \nx", 1, 1, 32);
        validParse("square 2", "square 2");
        validEvaluate(4, "square 2", 1, 1, 32);
        validParse("square 1", "square     1");
        validEvaluate(1, "square     1", 1, 1, 32);
        validParse("square x", "square   \nx");
        validEvaluate(100, "square   \nx", 10, 1, 32);
        validParse("log2 1", "log2 1");
        validEvaluate(0, "log2 1", 1, 1, 32);
        validParse("log2 1", "log2     1");
        validEvaluate(0, "log2     1", 1, 1, 32);
        validParse("log2 x", "log2   \nx");
        validEvaluate(16, "log2   \nx", 65600, 1, 32);
        validParse("pow2 1", "pow2 1");
        validEvaluate(2, "pow2 1", 1, 1, 32);
        validParse("pow2 1", "pow2     1");
        validEvaluate(2, "pow2     1", 1, 1, 32);
        validParse("pow2 x", "pow2   \nx");
        validEvaluate(4, "pow2   \nx", 2, 1, 32);
        validParse("reverse 1", "reverse 1");
        validEvaluate(1, "reverse 1", 1, 1, 32);
        validParse("reverse 1", "reverse     1");
        validEvaluate(1, "reverse     1", 1, 1, 32);
        validParse("reverse x", "reverse   \nx");
        validEvaluate(321, "reverse   \nx", 123, 1, 32);
        validParse("digits 1", "digits 1");
        validEvaluate(1, "digits 1", 1, 1, 32);
        validParse("digits 1", "digits     1");
        validEvaluate(1, "digits     1", 1, 1, 32);
        validParse("digits x", "digits   \nx");
        validEvaluate(6, "digits   \nx", 123, 1, 32);
    }

    @Test
    public void randomTests() {
        System.out.println("Ramdom parser tests" + ANSI_RESET);
        validParse("1 * 2 - 3", "1 * 2 - 3");
        validEvaluate(-1, "1 * 2 - 3", 123, 1, 32);
        validParse("1 * 2 - 3", "(1 * 2) - 3");
        validEvaluate(-1, "(1 * 2) - 3", 123, 1, 32);
        validParse("1 * (2 - 4)", "1 * (2 - 4)");
        validEvaluate(-2, "1 * (2 - 4)", 123, 1, 32);
        validParse("1 * (4 - 3) << x", "1 * (4 - 3) << x");
        validEvaluate(8, "1 * (4 - 3) << x", 3, 1, 32);
        validParse("1 * (5 - 3 << x)", "1 * (5 - 3 << x)");
        validEvaluate(16, "1 * (5 - 3 << x)", 3, 1, 32);
        validParse("2 + 3 * 4 ** 5", "2 + 3 * 4 ** 5");
        validEvaluate(3074, "2 + 3 * 4 ** 5", 123, 1, 32);
        validParse("1 << 2 + 3 * 4 ** 5", "1 << 2 + 3 * 4 ** 5");
        validEvaluate(4, "1 << 2 + 3 * 4 ** 5", 123, 1, 32);
        validParse("1 << 3 + 3 * 4 ** 5", "1 << (3 + 3 * 4 ** 5)");
        validEvaluate(8, "1 << (3 + 3 * 4 ** 5)", 123, 1, 32);
        validParse("1 << 2 + 3 * 4 ** 5", "1 << (2 + (3 * 4 ** 5))");
        validEvaluate(4, "1 << (2 + (3 * 4 ** 5))", 123, 1, 32);
        validParse("1 << 2 + 3 * 4 ** 5", "1 << (2 + (3 * (4 ** 5)))");
        validEvaluate(4, "1 << (2 + (3 * (4 ** 5)))", 123, 1, 32);
        validParse("2 + (3 * 4) ** 5", "(2 + (3 * 4) ** 5)");
        validEvaluate(248834, "(2 + (3 * 4) ** 5)", 123, 1, 32);
        validParse("1 << 2 + (3 * 4) ** 5", "1 << (2 + (3 * 4) ** 5)");
        validEvaluate(4, "1 << (2 + (3 * 4) ** 5)", 123, 1, 32);
        validParse("1 + 2 << 3 // 4", "1 + 2 << 3 // 4");
        validEvaluate(3, "1 + 2 << 3 // 4", 123, 1, 32);
        validParse("1 + 2 << 6 // 4", "1 + 2 << (6 // 4)");
        validEvaluate(6, "1 + 2 << (6 // 4)", 123, 1, 32);
        validParse("1 + 2 << 16 // 4", "(1 + 2) << 16 // 4");
        validEvaluate(12, "(1 + 2) << 16 // 4", 123, 1, 32);
        validParse("abs (abs (abs (abs (abs 1234))))", "abs abs abs abs abs 1234");
        validEvaluate(1234, "abs abs abs abs abs 1234", 123, 1, 32);
        validParse("abs (square (pow2 (log2 (abs -1234))))", "abs square pow2 log2 abs-1234");
        validEvaluate(1024 * 1024, "abs square pow2 log2 abs-1234", 123, 1, 32);
        validParse("abs 3 + square (pow2 (log2 (abs -1234)))", "abs 3 + square pow2 log2 abs-1234");
        validEvaluate(1024 * 1024 + 3, "abs 3 + square pow2 log2 abs-1234", 123, 1, 32);
        validParse("abs (-(square 3))", "abs - square 3");
        validEvaluate(9, "abs - square 3", 123, 1, 32);
    }

    private void validParse(final String valid, final String input) {
        Assert.assertEquals(valid, parse(input).toMiniString());
        System.out.println("\"" + input + "\" - " + ANSI_YELLOW + "Passed!" + ANSI_RESET);
    }

    private void validEvaluate(final int valid, final String input, int x, int y, int z) {
        Assert.assertEquals(valid, parse(input).evaluate(x, y, z));
        System.out.println("\"" + input + " = " + valid + "\", with args: (" + x + ", " + y + ", " + z + ") - "  + ANSI_YELLOW + "Passed!" + ANSI_RESET);
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
