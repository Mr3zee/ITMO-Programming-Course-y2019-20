package tests;

import expression.expression_tools.CommonExpression;
import expression.exceptions.*;
import expression.parser.ExpressionParser;
import expression.type.EType;
import org.junit.*;

import java.util.function.Function;

public abstract class MyParserTest<T extends Number> {
    private ExpressionParser<T> parser;
    private Function<String, EType<T>> parseEType;
    private String currentExceptionName;

    @Before
    public void setUp() {
        parseEType = getParseFunction();
        parser = new ExpressionParser<>(parseEType);
        System.out.format(Colors.paintCyan("(%s) Test description: "), testingTypeName());
    }

    protected abstract String testingTypeName();

    protected abstract Function<String, EType<T>> getParseFunction();

    private void printTestDescription(String name) {
        System.out.println(Colors.paintYellow(name));
    }

    @After
    public void tearDown() {
        System.out.println(Colors.paintCyan("Testing completed\n"));
    }

    @Test
    public void IllegalSymbolPEExceptionTests() {
        printTestDescription("Illegal symbols in expressions");
        currentExceptionName = "Unknown";
        implIllegalSymbolPEExceptionTests();
    }

    @Test
    public void MissingLexemePEExceptionTests() {
        printTestDescription("Missing lexemes in expressions");
        currentExceptionName = "Missing";
        implMissingLexemePEExceptionTests();
    }

    @Test
    public void ParenthesisAbsencePEExceptionTests() {
        printTestDescription("Missing parenthesises in expressions");
        currentExceptionName = "Expected";
        implNoParenthesisPEExceptionTests();
    }

    @Test
    public void EmptyExpressionPEExceptionTests() {
        printTestDescription("Empty expressions");
        currentExceptionName = "Expression";
        implEmptyExpressionPEException();
    }

    @Test
    public void UnsupportedCastPEExceptionTests() {
        printTestDescription("Unsupported casts in expressions");
        currentExceptionName = "Unsupported";
        implUnsupportedCastPEExceptionTests();
    }

    @Test
    public void WhitespacesTests() {
        printTestDescription("Expressions with redundant whitespaces");
        implWhitespacesTests();
    }

    @Test
    public void ParenthesisTests() {
        printTestDescription("Expressions with redundant parenthesises");
        implParenthesisTests();
    }

    @Test
    public void RandomTests() {
        printTestDescription("Random expressions");
        implRandomTests();
    }

    @Test
    public void ConstantOverflowPEExceptionTests() {
        printTestDescription("Overflow of constants in expression");
        currentExceptionName = "Overflow";
        implConstantOverflowPEExceptionTests();
    }

    @Test
    public void ConstantUnderflowPEExceptionTests() {
        printTestDescription("Underflow of constants in expression");
        currentExceptionName = "Underflow";
        implConstantUnderflowPEExceptionTests();
    }

    @Test
    public void DivisionByZeroEExceptionTests() {
        printTestDescription("Division by zero in expression");
        currentExceptionName = "Division";
        implDivisionByZeroEExceptionTests();
    }

    @Test
    public void OverflowEEExceptionTests() {
        printTestDescription("Overflow of variables in expression");
        currentExceptionName = "Overflow";
        implOverflowEEExceptionTests();
    }

    @Test
    public void UnderflowEEExceptionTests() {
        printTestDescription("Underflow of variables in expression");
        currentExceptionName = "Underflow";
        implUnderflowEEExceptionTests();
    }

    @Test
    public void PlusMinusTests() {
        printTestDescription("Plus and minus operations");
        implPlusMinusTests();
    }

    @Test
    public void MultiplyDivideTests() {
        printTestDescription("Multiply and Divide operations");
        implMultiplyDivideTests();
    }

    @Test
    public void NegateTests() {
        printTestDescription("Negate operation");
        implNegateTests();
    }

    @Test
    public void CountOperationTests() {
        printTestDescription("Count operation");
        implCountOperationTests();
    }

    @Test
    public void MinimumTests() {
        printTestDescription("Minimum operation");
        implMinimumTests();
    }

    @Test
    public void MaximumTests() {
        printTestDescription("Maximum operation");
        implMaximumTests();
    }

    protected abstract void implMinimumTests();

    protected abstract void implMaximumTests();

    protected abstract void implIllegalSymbolPEExceptionTests();

    protected abstract void implMissingLexemePEExceptionTests();

    protected abstract void implNoParenthesisPEExceptionTests();

    protected abstract void implEmptyExpressionPEException();

    protected abstract void implConstantOverflowPEExceptionTests();

    protected abstract void implConstantUnderflowPEExceptionTests();

    protected abstract void implDivisionByZeroEExceptionTests();

    protected abstract void implOverflowEEExceptionTests();

    protected abstract void implUnderflowEEExceptionTests();

    protected abstract void implUnsupportedCastPEExceptionTests();

    protected abstract void implPlusMinusTests();

    protected abstract void implMultiplyDivideTests();

    protected abstract void implWhitespacesTests();

    protected abstract void implParenthesisTests();

    protected abstract void implNegateTests();

    protected abstract void implRandomTests();

    protected abstract void implCountOperationTests();

    protected void validParseAndEvaluate(final String expression, final T x, final T y, final T z, final String expected, final T valid) {
        validParse(expected, expression);
        validEvaluate(valid, expression, x, y, z);
    }

    protected void validEvaluate(final T valid, final String input, T x, T y, T z) {
        T found = getExpressionValue(input, x, y, z);
        Assert.assertEquals(valid, found);
        System.out.println("\"" + input + " = " + valid + "\", with args: (" + x + ", " + y + ", " + z + ") - "  + passedMessage());
    }

    protected void invalidEvaluate(final String input, final T x, final T y, final T z) {
        try {
            T found = getExpressionValue(input, x, y, z);
            Assert.fail("Evaluating failure expected for \"" + input + "\", found \"" + found + "\"");
        } catch (EvaluatingExpressionException e) {
            checkExceptionName(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    protected void validParse(final String valid, final String input) {
        Assert.assertEquals(valid, parse(input).toMiniString());
        System.out.println("\"" + input + "\" - " + passedMessage());
    }

    protected void invalidParse(final String input, final int pos) {
        try {
            fail(input);
        } catch (ParsingExpressionException e) {
            checkExceptionMessage(e.getMessage(), pos);
            System.out.println(e.getMessage());
        }
    }

    protected void invalidParse(final String input) {
        try {
            fail(input);
        } catch (ParsingExpressionException e) {
            checkExceptionName(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private void fail(final String input) {
        String found = parse(input).toMiniString();
        Assert.fail("Parsing failure expected for \"" + input + "\", found \"" + found);
    }

    private CommonExpression<T> parse(final String input) {
        return parser.parse(input);
    }

    private T getExpressionValue(String input, T x, T y, T z) {
        EType<T> xt = parseEType.apply(String.valueOf(x));
        EType<T> yt = parseEType.apply(String.valueOf(y));
        EType<T> zt = parseEType.apply(String.valueOf(z));
        return toEvaluate(input, xt, yt, zt);
    }

    private T toEvaluate(String input, EType<T> xt, EType<T> yt, EType<T> zt) {
        return parse(input).evaluate(xt, yt, zt).value();
    }

    private void checkExceptionMessage(String message, int position) {
        checkExceptionPosition(message, position);
        checkExceptionName(message);
    }

    private void checkExceptionPosition(String exceptionMessage, int position) {
        Assert.assertEquals(position, getPosition(exceptionMessage));
    }

    private void checkExceptionName(String exceptionMessage) {
        Assert.assertEquals(currentExceptionName, getExceptionName(exceptionMessage));
    }

    private String passedMessage() {
        return Colors.paintYellow("Passed!");
    }

    @SuppressWarnings("StatementWithEmptyBody")
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

    @SuppressWarnings("StatementWithEmptyBody")
    private String getExceptionName(final String message) {
        int i = 0;
        while (!Character.isWhitespace(message.charAt(i++))) {}
        int j = i;
        while (!Character.isWhitespace(message.charAt(i++))) {}
        return message.substring(j, i - 1);
    }

    protected void notAvailable() {
        System.out.println(Colors.paintRed("This test is not available here"));
    }
}
