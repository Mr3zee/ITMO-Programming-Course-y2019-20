package tests;

import expression.type.DoubleEType;
import expression.type.EType;

import java.util.function.Function;

public class DoubleParserTest extends ParserTest<Double> {
    @Override
    protected String testingTypeName() {
        return "Double";
    }

    @Override
    protected Function<String, EType<Double>> getParseFunction() {
        return DoubleEType::parseDouble;
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
    protected void implDivisionByZeroEExceptionTests() {
        notAvailable();
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
    protected void implUnsupportedCastPEExceptionTests() {
        notAvailable();
    }

    @Override
    protected void implPlusMinusTests() {
        validParseAndEvaluate("x + y", 1.0, 1.0, 1.0, "x + y", 2.0);
        validParseAndEvaluate("x + z", 1.0, 1.0, 1.0, "x + z", 2.0);
        validParseAndEvaluate("123.0 + 23", 1.0, 1.0, 1.0, "123.0 + 23.0", 146.0);
        validParseAndEvaluate("x - z", 1.0, 1.0, 1.0, "x - z", 0.0);
        validParseAndEvaluate("z - y", 1.0, 13.0, 2.0, "z - y", -11.0);
        validParseAndEvaluate("6 - 10", 1.0, 13.0, 2.0, "6.0 - 10.0", -4.0);
        validParseAndEvaluate("z - y - 1.0", 1.0, 13.0, 2.0, "z - y - 1.0", -12.0);
        validParseAndEvaluate("z + y + 1.0", 1.0, 13.0, 2.0, "z + y + 1.0", 16.0);
    }

    @Override
    protected void implMultiplyDivideTests() {
        validParseAndEvaluate("x * y", 1.0, 13.0, 2.0, "x * y", 13.0);
        validParseAndEvaluate("x * z", 1.0, 13.0, 2.0, "x * z", 2.0);
        validParseAndEvaluate("123 * 23", 1.0, 1.0, 1.0, "123.0 * 23.0", 2829.0);
        validParseAndEvaluate("x / z", 1.0, 13.0, 2.0, "x / z", 0.5);
        validParseAndEvaluate("z / y", 1.0, 13.0, 22.0, "z / y", (double) 22 / 13);
        validParseAndEvaluate("6 / 10", 1.0, 13.0, 2.0, "6.0 / 10.0", 0.6);
        validParseAndEvaluate("z / y / 1.0", 1.0, 13.0, 26.0, "z / y / 1.0", 2.0);
        validParseAndEvaluate("z * y * 1.0", 1.0, 13.0, 26.0, "z * y * 1.0", 338.0);
    }

    @Override
    protected void implNegateTests() {
        validParseAndEvaluate("(-x)", -1024.0, 1.0, 32.0, "-x",1024.0);
        validParseAndEvaluate("-(x)", -1024.0, 1.0, 32.0, "-x",1024.0);
        validParseAndEvaluate("-(x - y)", -1024.0, 1.0, 32.0, "-(x - y)",1025.0);
        validParseAndEvaluate("-      (x - y)", -1024.0, 1.0, 32.0, "-(x - y)",1025.0);
    }

    @Override
    protected void implCountOperationTests() {
        validParseAndEvaluate("count 11", 1.0, 1.0, 1.0, "count 11.0", validBits(11.0));
        validParseAndEvaluate("count -1", 1.0, 1.0, 1.0, "count -1.0", validBits(-1.0));
        validParseAndEvaluate("count x", (double) Integer.MAX_VALUE, 1.0, 1.0, "count x", validBits(Integer.MAX_VALUE));
        validParseAndEvaluate("count y", 1.0, (double) Integer.MIN_VALUE, 1.0, "count y", validBits(Integer.MIN_VALUE));
        validParseAndEvaluate("count       z", 1.0, 1.0, 0.0, "count z", validBits(0.0));
    }

    private double validBits(double val) {
        return Long.bitCount(Double.doubleToLongBits(val));
    }
}
