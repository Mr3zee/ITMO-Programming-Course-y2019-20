package tests;

import expression.type.EType;
import expression.type.IntegerEType;

import java.util.function.Function;

public class GeneralParserTest extends ParserTest<Integer> {
    @Override
    protected String testingTypeName() {
        return "General";
    }

    @Override
    protected Function<String, EType<Integer>> getParseFunction() {
        return IntegerEType::parseCheckedInteger;
    }

    @Override
    protected void implIllegalSymbolPEExceptionTests() {
        invalidParse("@ x + y", 0);
        invalidParse("@x + y", 0);
        invalidParse("x@ + y", 1);
        invalidParse("x @ + y", 2);
        invalidParse("x @+ y", 2);
        invalidParse("x +@ y", 3);
        invalidParse("x + @ y", 4);
        invalidParse("x + @y", 4);
        invalidParse("x + @@@@@@@y", 4);
        invalidParse("x + y@", 5);
        invalidParse("x + y       @", 12);
        invalidParse("1a", 1);
        invalidParse("1a2", 1);
        invalidParse("-1a", 2);
        invalidParse("1 ab 2", 2);
        invalidParse("1ab 2", 1);
        invalidParse("counts 5", 0);
        invalidParse("caunt 5", 0);
    }

    @Override
    protected void implMissingLexemePEExceptionTests() {
        invalidParse("x y", 2);
        invalidParse("x  y", 3);
        invalidParse("+x  y", 0);
        invalidParse("+", 0);
        invalidParse("-", 1);
        invalidParse("x ++ y", 3);
        invalidParse("x + + y", 4);
        invalidParse("x - - +y", 6);
        invalidParse("1 1", 2);
        invalidParse("1   1", 4);
        invalidParse("* 1   1", 0);
        invalidParse(" 1 * /  1", 5);
        invalidParse("(1 + 2) (3 + 4)", 8);
        invalidParse("(1 + 2)(3 + 4)", 7);
        invalidParse("(1 + 2)(", 7);
        invalidParse("(1 + 2) xyz", 8);
        invalidParse("(1 + 2)xyz", 7);
        invalidParse("xyz (1 + 2)", 1);
        invalidParse("y(1 + 2)", 1);
        invalidParse("z (1 + 2)", 2);
        invalidParse("()", 1);
        invalidParse("(())", 2);
        invalidParse("count + 1", 6);
        invalidParse("count * 1", 6);
        invalidParse("count /1", 6);
    }

    @Override
    protected void implNoParenthesisPEExceptionTests() {
        invalidParse("x + y)", 5);
        invalidParse("(x + y", 6);
        invalidParse("(x + y))", 7);
        invalidParse("x + (y", 6);
        invalidParse("x) + y", 1);
        invalidParse("((x) + y))", 9);
        invalidParse("((((((((((x)))))))))))", 21);
    }

    @Override
    protected void implEmptyExpressionPEException() {
        invalidParse("");
        invalidParse(" ");
        invalidParse("  ");
        invalidParse("\n");
        invalidParse("\t");
        invalidParse("\r");
    }

    @Override
    protected void implWhitespacesTests() {
        validParse("x + y", "x+y");
        validParse("x + y", "x    +y");
        validParse("x + y", "x    +       y");
        validParse("x + y", "x+       y");
        validParse("x + y", "x\t\n \t\r+\n\n\n\n\n\n\n\r\r\r\r       y");
    }

    @Override
    protected void implParenthesisTests() {
        validParse("x", "(x)");
        validParse("x", "(((((x)))))");
        validParse("x - y", "((((((x))))) - (y))");
        validParse("x - (y - z)", "((((((x))))) - ((y) - z))");
        validParse("x - (y - (1 - (2 - (3 - 4))))", "(x - (y - (1 - (2 - ( 3 - 4)))))");
        validParse("11234567", "(             11234567\n)");
    }

    @Override
    protected void implRandomTests() {
        validParseAndEvaluate("1 * 2 - 3", 123, 1, 32, "1 * 2 - 3", -1);
        validParseAndEvaluate("(1 * 2) - 3", 123, 1, 32, "1 * 2 - 3", -1);
        validParseAndEvaluate("1 * (2 - 4)", 123, 1, 32, "1 * (2 - 4)", -2);
        validParseAndEvaluate("count count count x + count(y + z)", 31, 1024, 1, "count (count (count x)) + count (y + z)", 3);
    }

    @Override
    protected void implUnsupportedCastPEExceptionTests() {
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
    protected void implPlusMinusTests() {
        notAvailable();
    }

    @Override
    protected void implMultiplyDivideTests() {
        notAvailable();
    }

    @Override
    protected void implNegateTests() {
        notAvailable();
    }

    @Override
    protected void implCountOperationTests() {
        notAvailable();
    }

    @Override
    protected void implMinimumTests() {
        notAvailable();
    }

    @Override
    protected void implMaximumTests() {
        notAvailable();
    }
}
