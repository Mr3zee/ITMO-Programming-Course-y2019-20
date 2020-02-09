package expression.exceptions;

import expression.*;
import expression.exceptions.expExceptions.*;
import expression.parser.*;

import java.util.Map;
import java.util.Set;

public class ExpressionParser extends BaseParser implements Parser {

    private final Set<Character> LEXEMES = Set.of ('0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'x', 'y', 'z', '+', '-', '*', '/', '(', ')', '<', '>', '\0');
    private final Set<Character> OPERATIONS = Set.of('+', '-', '*', '/', '\0', ')');
    private final Map<Character, String> LONG_OPS = Map.of('a', "abs", 's', "square");
    private Lexeme lastLexeme;

    @Override
    public CommonExpression parse(String expression) throws ParsingExpressionException {
        source = new ExpressionSource(expression);
        nextChar();
        skipWhitespaces();
        if (!hasNext()) {
            throw new EmptyExpressionPEException();
        }
        lastLexeme = Lexeme.START;
        CommonExpression result = expressionParse();
        if (hasNext()) {
            throw new NoParenthesisPEException("opening", getNext(LONG_OPS, LEXEMES));
        }
        return result;
    }

    private CommonExpression expressionParse() throws ParsingExpressionException {
        CommonExpression result = shiftsParse();
        skipWhitespaces();
        while (true) {
            if (test('<', '>')) {
                lastLexeme = Lexeme.OP;
                if (test('<')) {
                    compareLexeme("<<");
                    result = new LeftShift(result, shiftsParse());
                } else if (test('>')) {
                    compareLexeme(">>");
                    result = new RightShift(result, shiftsParse());
                }
            } else {
                if (!OPERATIONS.contains(getCurrentLex())) {
                    throw missingLexemeHandler();
                }
                break;
            }
            skipWhitespaces();
        }
        return result;
    }


    private CommonExpression shiftsParse() throws ParsingExpressionException {
        CommonExpression result = additiveParse();
        skipWhitespaces();
        while (test('+', '-')) {
            lastLexeme = Lexeme.OP;
            if (compare('+')) {
                result = new CheckedAdd(result, additiveParse());
            } else if (compare('-')) {
                result = new CheckedSubtract(result, additiveParse());
            }
            skipWhitespaces();
        }
        return result;
    }

    private CommonExpression additiveParse() throws ParsingExpressionException {
        CommonExpression result = multiplicativeParse();
        skipWhitespaces();
        while (test('*', '/')) {
            lastLexeme = Lexeme.OP;
            if (compare('*')) {
                result = new CheckedMultiply(result, multiplicativeParse());
            } else if (compare('/')) {
                result = new CheckedDivide(result, multiplicativeParse());
            }
            skipWhitespaces();
        }
        return result;
    }

    private CommonExpression multiplicativeParse() throws ParsingExpressionException {
        skipWhitespaces();
        if (compare('(')) {
            lastLexeme = Lexeme.OPAR;
            CommonExpression result = expressionParse();
            if (compare(')')) {
                lastLexeme = Lexeme.CPAR;
                return result;
            }
            throw new NoParenthesisPEException("closing", getNext(LONG_OPS, LEXEMES));
        } else if (test('a')) {
            compareLexeme("abs");
            lastLexeme = Lexeme.OP;
            return new CheckedAbs(multiplicativeParse());
        } else if (test('s')) {
            compareLexeme("square");
            lastLexeme = Lexeme.OP;
            return new CheckedSquare(multiplicativeParse());
        } else if (test('x', 'y', 'z')) {
            String name = String.valueOf(getCurrentLex());
            nextChar();
            lastLexeme = Lexeme.VAR;
            return new Variable(name);
        } else if (isDigit()) {
            lastLexeme = Lexeme.NUM;
            return parseNumber(takeNumber());
        } else if (compare('-')) {
            if (isDigit()) {
                lastLexeme = Lexeme.NUM;
                return parseNumber("-" + takeNumber());
            }
            lastLexeme = Lexeme.OP;
            return new CheckedNegate(multiplicativeParse());
        }
        throw missingLexemeHandler();
    }

    private void compareLexeme(String expect) {
        int position = source.getPosition() - 1;
        String res = takeWord(expect);
        if (!res.equals(expect)) {
            char c = getCurrentLex();
            throw new InvalidOperatorFormPEException(expect, new FoundNextInfo(res + (c != '\0'? c : ""), position, source.getExpression()));
        }
    }

    private CommonExpression parseNumber(String number) throws ParsingExpressionException {
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
        if (isDigit()) {
            throw new MissingLexemePEException(lastLexeme.getName(), getNext(LONG_OPS, LEXEMES));
        }
        FoundNextInfo next = getNext(LONG_OPS, LEXEMES);
        Operations cur = LexemeConvertor.convert(next.getNext());
        if (next.getNext().length() == 0 || cur != Operations.INVALID) {
            return new MissingLexemePEException(lastLexeme.getName(), next);
        }
        return new IllegalSymbolPEException(next);
    }
}