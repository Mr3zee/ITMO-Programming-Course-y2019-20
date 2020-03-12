package tests;

import expression.type.EType;
import expression.type.IntegerEType;
import tests.ParserTest;

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
    protected void implWhitespacesTests() {
        notAvailable();
    }

    @Override
    protected void implParenthesisTests() {
        notAvailable();
    }

    @Override
    protected void implRandomTests() {
        notAvailable();
    }

    @Override
    protected void implUnsupportedCastPEExceptionTests() {
        invalidParse("10.1", 0);
        invalidParse("0.1", 0);
        invalidParse("10.0", 0);
    }

    @Override
    protected void implConstantOverflowPEExceptionTests() {
        invalidParse("2147483649", 0);
        invalidParse(" 2147483649", 1);
        invalidParse("x + 111111111111111111111111111111", 4);
    }

    @Override
    protected void implConstantUnderflowPEExceptionTests() {
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
        validParseAndEvaluate("x + y", 1, 1, 1, "x + y", 2);
        validParseAndEvaluate("x + z", 1, 1, 1, "x + z", 2);
        validParseAndEvaluate("123 + 23", 1, 1, 1, "123 + 23", 146);
        validParseAndEvaluate("x - z", 1, 1, 1, "x - z", 0);
        validParseAndEvaluate("z - y", 1, 13, 2, "z - y", -11);
        validParseAndEvaluate("6 - 10", 1, 13, 2, "6 - 10", -4);
        validParseAndEvaluate("z - y - 1", 1, 13, 2, "z - y - 1", -12);
        validParseAndEvaluate("z + y + 1", 1, 13, 2, "z + y + 1", 16);
    }

    @Override
    protected void implMultiplyDivideTests() {
        validParseAndEvaluate("x * y", 1, 13, 2, "x * y", 13);
        validParseAndEvaluate("x * z", 1, 13, 2, "x * z", 2);
        validParseAndEvaluate("123 * 23", 1, 1, 1, "123 * 23", 2829);
        validParseAndEvaluate("x / z", 1, 13, 2, "x / z", 0);
        validParseAndEvaluate("z / y", 1, 13, 22, "z / y", 1);
        validParseAndEvaluate("6 / 10", 1, 13, 2, "6 / 10", 0);
        validParseAndEvaluate("z / y / 1", 1, 13, 26, "z / y / 1", 2);
        validParseAndEvaluate("z * y * 1", 1, 13, 26, "z * y * 1", 338);
    }

    @Override
    protected void implNegateTests() {
        validParseAndEvaluate("(-x)", -1024, 1, 32, "-x",1024);
        validParseAndEvaluate("-(x)", -1024, 1, 32, "-x",1024);
        validParseAndEvaluate("-(x - y)", -1024, 1, 32, "-(x - y)",1025);
        validParseAndEvaluate("-      (x - y)", -1024, 1, 32, "-(x - y)",1025);
    }

    @Override
    protected void implCountOperationTests() {
        validParseAndEvaluate("count 11", 1, 1, 1, "count 11", 3);
        validParseAndEvaluate("count -1", 1, 1, 1, "count -1", 32);
        validParseAndEvaluate("count x", Integer.MAX_VALUE, 1, 1, "count x", 31);
        validParseAndEvaluate("count y", 1, Integer.MIN_VALUE, 1, "count y", 1);
        validParseAndEvaluate("count       z", 1, 1, 0, "count z", 0);
    }

    @Override
    protected void implMinimumTests() {
        validParseAndEvaluate("1 min 2", 1, 1, 1, "1 min 2", 1);
        validParseAndEvaluate("-1 min 2", 1, 1, 1, "-1 min 2", -1);
        validParseAndEvaluate("-1 + 4 min 2", 1, 1, 1, "-1 + 4 min 2", 2);
        validParseAndEvaluate("-1 + 4 min 2 * 3", 1, 1, 1, "-1 + 4 min 2 * 3", 3);
    }

    @Override
    protected void implMaximumTests() {
        validParseAndEvaluate("1 max 2", 1, 1, 1, "1 max 2", 2);
        validParseAndEvaluate("-1 max 2", 1, 1, 1, "-1 max 2", 2);
        validParseAndEvaluate("-1 + 4 max 2", 1, 1, 1, "-1 + 4 max 2", 3);
        validParseAndEvaluate("-1 + 4 max 2 * 3", 1, 1, 1, "-1 + 4 max 2 * 3", 6);
    }
}
