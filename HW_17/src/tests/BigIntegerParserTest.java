package tests;

import expression.type.BigIntegerEType;
import expression.type.EType;

import java.math.BigInteger;
import java.util.function.Function;

public class BigIntegerParserTest extends ParserTest<BigInteger> {
    final BigInteger ZERO = BigInteger.ZERO;
    final BigInteger ONE = BigInteger.ONE;
    final BigInteger TWO = new BigInteger("2");
    final BigInteger BIG = new BigInteger("99999999999999999");
    final BigInteger SMALL = new BigInteger("-99999999999999999");

    @Override
    protected String testingTypeName() {
        return "BigInteger";
    }

    @Override
    protected Function<String, EType<BigInteger>> getParseFunction() {
        return BigIntegerEType::parseBigInteger;
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
    protected void implParenthesisTests() {
        notAvailable();
    }

    @Override
    protected void implRandomTests() {
        notAvailable();
    }

    @Override
    protected void implConstantOverflowPEExceptionTests() {
        notAvailable();
    }

    @Override
    protected void implConstantUnderflowPEExceptionTests() {
        notAvailable();
    }

    @Override
    protected void implUnsupportedCastPEExceptionTests() {
        invalidParse("100000000000000000000000000.0", 0);
        invalidParse("0.0", 0);
        invalidParse("0.1", 0);
    }

    @Override
    protected void implDivisionByZeroEExceptionTests() {
        invalidEvaluate("(x / 0)", ONE, ONE, ONE);
        invalidEvaluate("x / 0", ONE, ONE, ONE);
        invalidEvaluate("x / (y - z)", ONE, ONE, ONE);
        invalidEvaluate("x / y", ONE, ZERO, ONE);
        invalidEvaluate("x / z", ONE, ONE, ZERO);
        invalidEvaluate("0 / 0", ONE, ONE, ONE);
    }

    @Override
    protected void implOverflowEEExceptionTests() {
        notAvailable();
    }

    @Override
    protected void implUnderflowEEExceptionTests() {
        notAvailable();
    }

    @Override
    protected void implPlusMinusTests() {
        validParseAndEvaluate("x + y", ONE, ONE, ONE, "x + y", TWO);
        validParseAndEvaluate("x + y", SMALL, BIG, ONE, "x + y", ZERO);
        validParseAndEvaluate("x + z", ONE, ONE, ONE, "x + z", TWO);
        validParseAndEvaluate("123 + 23", ONE, ONE, ONE, "123 + 23", toBi(146));
        validParseAndEvaluate("x - z", ONE, ONE, ONE, "x - z", ZERO);
        validParseAndEvaluate("z - y", ONE, toBi(13), TWO, "z - y", toBi(-11));
        validParseAndEvaluate("6 - 10", ONE, toBi(13), TWO, "6 - 10", toBi(-4));
        validParseAndEvaluate("z - y - 1", ONE, toBi(13), TWO, "z - y - 1", toBi(-12));
        validParseAndEvaluate("z + y + 1", ONE, toBi(13), TWO, "z + y + 1", toBi(16));
    }

    @Override
    protected void implMultiplyDivideTests() {
        validParseAndEvaluate("x * y", ONE, toBi(13), TWO, "x * y", toBi(13));
        validParseAndEvaluate("x * z", ONE, toBi(13), TWO, "x * z", TWO);
        validParseAndEvaluate("123 * 23", ONE, ONE, ONE, "123 * 23", toBi(2829));
        validParseAndEvaluate("x / z", ONE, toBi(13), TWO, "x / z", ZERO);
        validParseAndEvaluate("z / y", ONE, toBi(13), toBi(21), "z / y", ONE);
        validParseAndEvaluate("6 / 10", ONE, toBi(13), TWO, "6 / 10", ZERO);
        validParseAndEvaluate("z / y / 1", ONE, toBi(13), toBi(26), "z / y / 1", TWO);
        validParseAndEvaluate("z * y * 1", ONE, toBi(13), toBi(26), "z * y * 1", toBi(338));
        validParseAndEvaluate("z / y", ONE, SMALL, BIG, "z / y", toBi(-1));
    }

    @Override
    protected void implNegateTests() {
        validParseAndEvaluate("(-x)", toBi(-1024), ONE, toBi(32), "-x",toBi(1024));
        validParseAndEvaluate("-(x)", toBi(-1024), ONE, toBi(32), "-x",toBi(1024));
        validParseAndEvaluate("-(x - y)", toBi(-1024), ONE, toBi(32), "-(x - y)",toBi(1025));
        validParseAndEvaluate("-      (x - y)", toBi(-1024), ONE, toBi(32), "-(x - y)",toBi(1025));
    }

    @Override
    protected void implCountOperationTests() {
        validParseAndEvaluate("count 11", ONE, ONE, ONE, "count 11", toBi(3));
        validParseAndEvaluate("count -1", ONE, ONE, ONE, "count -1", toBi(0));
        validParseAndEvaluate("count x", BIG, ONE, ONE, "count x", toBi(BIG.bitCount()));
        validParseAndEvaluate("count y", ONE, SMALL, ONE, "count y", toBi(SMALL.bitCount()));
        validParseAndEvaluate("count       z", ONE, ONE, ZERO, "count z", ZERO);
    }

    private BigInteger toBi(int v) {
        return new BigInteger(String.valueOf(v));
    }
}
