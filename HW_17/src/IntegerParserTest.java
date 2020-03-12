import expression.type.EType;
import expression.type.IntegerEType;

import java.util.function.Function;

public class IntegerParserTest extends ParserTest<Integer> {
    @Override
    protected String testingTypeName() {
        return "Integer";
    }

    @Override
    protected Function<String, EType<Integer>> getParseFunction() {
        return IntegerEType::parseInteger;
    }

    @Override
    protected void implIllegalSymbolPEExceptionTests() {
        notAvailable();
    }

    @Override
    protected void implMissingLexemePEExceptionTests() {
        notAvailable();
    }

    @Override
    protected void implNoParenthesisPEExceptionTests() {
        notAvailable();
    }

    @Override
    protected void implEmptyExpressionPEException() {
        notAvailable();
    }

    @Override
    protected void implWhitespacesTest() {
        notAvailable();
    }

    @Override
    protected void implParenthesisTest() {
        notAvailable();
    }

    @Override
    protected void implConstantOverflowPEException() {
        invalidParse("2147483649", 0);
        invalidParse(" 2147483649", 1);
        invalidParse("x + 111111111111111111111111111111", 4);
    }

    @Override
    protected void implConstantUnderflowPEException() {
        invalidParse("-2147483649", 0);
        invalidParse(" -2147483649", 1);
        invalidParse("x + -2147483649", 4);
        invalidParse("x + -1111111111111111111111111", 4);
    }

    @Override
    protected void implDivisionByZeroEExceptionTests() {
        invalidEvaluate("x / 0", 0, 1, 1);
        invalidEvaluate("x / 0", 1, 1, 1);
        invalidEvaluate("x / (y - z)", 1, 1, 1);
        invalidEvaluate("x / y", 1, 0, 1);
        invalidEvaluate("x / z", 1, 1, 0);
        invalidEvaluate("0 / 0", 1, 1, 1);
    }

    @Override
    protected void implOverflowEEExceptionTests() {
        invalidEvaluate("2147483647 + 1", 0, 0, 0);
        invalidEvaluate("2147483647 + x", 1, 0, 0);
        invalidEvaluate("2147483647 * x", 2, 0, 0);
        invalidEvaluate("-2147483648 / x", -1, 0, 0);
        invalidEvaluate("-2147483648 / x + y", -2, Integer.MAX_VALUE, 0);
    }

    @Override
    protected void implUnderflowEEExceptionTests() {
        invalidEvaluate("x - y", Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        invalidEvaluate("x * y", Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
    }

    @Override
    protected void implPlusMinusTests() {
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

    @Override
    protected void implMultiplyDivideTest() {
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

    @Override
    protected void implUnaryOperationsTest() {
        validParse("-x", "(-x)");
        validEvaluate(1024, "(-x)", -1024, 1, 32);
        validParse("-x", "-(x)");
        validEvaluate(1024, "-(x)", -1024, 1, 32);
        validParse("-(x - y)", "-(x - y)");
        validEvaluate(1025, "-(x - y)", -1024, 1, 32);
        validParse("-(x - y)", "-      (x - y)");
        validEvaluate(1025, "-      (x - y)", -1024, 1, 32);
    }

    @Override
    protected void implRandomTests() {
        validParse("1 * 2 - 3", "1 * 2 - 3");
        validEvaluate(-1, "1 * 2 - 3", 123, 1, 32);
        validParse("1 * 2 - 3", "(1 * 2) - 3");
        validEvaluate(-1, "(1 * 2) - 3", 123, 1, 32);
        validParse("1 * (2 - 4)", "1 * (2 - 4)");
        validEvaluate(-2, "1 * (2 - 4)", 123, 1, 32);
    }
}
