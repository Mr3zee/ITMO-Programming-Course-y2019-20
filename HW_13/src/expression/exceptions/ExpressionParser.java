package expression.exceptions;

import expression.*;
import expression.exceptions.expExceptions.*;
import expression.parser.*;

import java.util.Map;
import java.util.Set;

public class ExpressionParser extends BaseParser implements Parser {
    private final Set<Character> BINARY_END_OPS = Set.of('+', '-', '*', '/', '\0', ')');
    private Lexeme lastLexeme;

    public ExpressionParser() {
        super(Set.of ('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'x', 'y', 'z', '+', '-', '*', '/', '(', ')', '<', '>', '\0'),
                Map.of('a', "abs", 's', "square", '<', "<<", '>', ">>",
                        'p', "pow2", 'l', "log2", '*', "**", '/', "//",
                        'r', "reverse", 'd', "digits"));
    }

    @Override
    public CommonExpression parse(final String expression) throws ParsingExpressionException {
        source = new ExpressionSource(expression);
        nextChar();
        skipWhitespaces();
        if (!hasNext()) {
            throw new EmptyExpressionPEException();
        }
        lastLexeme = Lexeme.START;
        CommonExpression result = expressionParse();
        if (hasNext()) {
            throw new NoParenthesisPEException("opening", getNext());
        }
        return result;
    }

    private CommonExpression expressionParse() throws ParsingExpressionException {
        CommonExpression result = shiftsParse();
        while (true) {
            if (test('<', '>')) {
                if (test('<')) {
                    checkLexeme("<<");
                    lastLexeme = Lexeme.LSHIFT;
                    result = new LeftShift(result, shiftsParse());
                } else if (test('>')) {
                    checkLexeme(">>");
                    lastLexeme = Lexeme.RSHIFT;
                    result = new RightShift(result, shiftsParse());
                }
            } else {
                if (!BINARY_END_OPS.contains(getCurrentLex())) {
                    throw missingLexemeHandler();
                }
                break;
            }
        }
        return result;
    }


    private CommonExpression shiftsParse() throws ParsingExpressionException {
        CommonExpression result = additiveParse();
        while (test('+', '-')) {
            if (compare('+')) {
                lastLexeme = Lexeme.PLUS;
                result = new CheckedAdd(result, additiveParse());
            } else if (compare('-')) {
                lastLexeme = Lexeme.MINUS;
                result = new CheckedSubtract(result, additiveParse());
            }
        }
        return result;
    }

    private CommonExpression additiveParse() throws ParsingExpressionException {
        CommonExpression result = multiplicativeParse();
        while (test('*', '/')) {
            if (compare('*')) {
                lastLexeme = Lexeme.MULT;
                result = new CheckedMultiply(result, multiplicativeParse());
            } else if (compare('/')) {
                lastLexeme = Lexeme.DIV;
                result = new CheckedDivide(result, multiplicativeParse());
            }
        }
        return result;
    }

    private CommonExpression multiplicativeParse() throws ParsingExpressionException {
        CommonExpression result = lowLevelParse();
        skipWhitespaces();
        while (test('*', '/')) {
            char cur = getCurrentLex();
            nextChar();
            if (test(cur)) {
                if (compare('*')) {
                    lastLexeme = Lexeme.POW;
                    result = new CheckedPower(result, lowLevelParse());
                } else if (compare('/')) {
                    lastLexeme = Lexeme.LOG;
                    result = new CheckedLogarithm(result, lowLevelParse());
                }
                skipWhitespaces();
            } else {
                getBack();
                break;
            }
        }
        return result;
    }

    private CommonExpression lowLevelParse() throws ParsingExpressionException {
        skipWhitespaces();
        if (compare('(')) {
            lastLexeme = Lexeme.OPAR;
            CommonExpression result = expressionParse();
            if (compare(')')) {
                lastLexeme = Lexeme.CPAR;
                return result;
            }
            throw new NoParenthesisPEException("closing", getNext());
        } else if (test('a')) {
            return new CheckedAbs(wordOpsHandler("abs", Lexeme.ABS));
        } else if (test('s')) {
            return new CheckedSquare(wordOpsHandler("square", Lexeme.SQR));
        } else if (test('p')) {
            return new CheckedPow2(wordOpsHandler("pow2", Lexeme.POW2));
        } else if (test('l')) {
            return new CheckedLog2(wordOpsHandler("log2", Lexeme.LOG2));
        } else if (test('d')) {
            return new Digits(wordOpsHandler("digits", Lexeme.DIG));
        } else if (test('r')) {
            return new CheckedReverse(wordOpsHandler("reverse", Lexeme.REV));
        } else if (test('x', 'y', 'z')) {
            char c = getCurrentLex();
            lastLexeme = c == 'x' ? Lexeme.X : c == 'y' ? Lexeme.Y : Lexeme.Z;
            String name = String.valueOf(c);
            nextChar();
            return new Variable(name);
        } else if (isDigit()) {
            lastLexeme = Lexeme.NUM;
            return parseNumber(takeNumber());
        } else if (compare('-')) {
            if (isDigit()) {
                lastLexeme = Lexeme.NUM;
                return parseNumber("-" + takeNumber());
            }
            lastLexeme = Lexeme.MINUS;
            return new CheckedNegate(lowLevelParse());
        }
        throw missingLexemeHandler();
    }

    private CommonExpression wordOpsHandler(String expected, Lexeme lexeme) {
        checkLexeme(expected);
        if (Character.isWhitespace(getCurrentLex()) || test('-', '(', '\0')) {
            lastLexeme = lexeme;
            return lowLevelParse();
        }
        throw new MissingWhitespacePEException(lexeme.getName(), getNext());
    }

    private void checkLexeme(final String expect) {
        int position = source.getPosition() - 1;
        String res = takeWord(expect);
        if (!res.equals(expect)) {
            throw new InvalidOperatorFormPEException(expect, new FoundNextInfo(res + getCurrentLex(), position, source.getExpression()));
        }
    }

    private CommonExpression parseNumber(final String number) throws ParsingExpressionException {
        try {
            int num = Integer.parseInt(number);
            return new Const(num);
        } catch (NumberFormatException e) {
            int position = source.getPosition() - number.length() - 1;
            if (number.charAt(0) == '-') {
                throw new ConstantUnderflowPEException(number, position, source.getExpression());
            }
            throw new ConstantOverflowPEException(number, position, source.getExpression());
        }
    }

    private ParsingExpressionException missingLexemeHandler() {
        FoundNextInfo next = getNext();
        if (find(next.getNext())) {
            return new MissingLexemePEException(lastLexeme.getName(), next);
        }
        return new IllegalSymbolPEException(next);
    }
}