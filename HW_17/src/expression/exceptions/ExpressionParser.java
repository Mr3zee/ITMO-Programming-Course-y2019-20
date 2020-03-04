package expression.exceptions;

import expression.*;
import expression.exceptions.EExceptions.*;
import expression.parser.*;

import java.util.Map;
import java.util.Set;

public class ExpressionParser extends BaseParser implements Parser {
    private final Set<Character> BINARY_END_OPS = Set.of('*', '/', '\0', ')');
    private Lexeme lastLexeme;

    public ExpressionParser() {
        super(Set.of ('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'x', 'y', 'z', '+', '-', '*', '/', '(', ')', '\0'));
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
        if (!BINARY_END_OPS.contains(getCurrentLex())) {
            throw missingLexemeHandler();
        }
        return result;
    }

    private CommonExpression additiveParse() throws ParsingExpressionException {
        CommonExpression result = lowLevelParse();
        skipWhitespaces();
        while (test('*', '/')) {
            if (compare('*')) {
                lastLexeme = Lexeme.MULT;
                result = new CheckedMultiply(result, lowLevelParse());
            } else if (compare('/')) {
                lastLexeme = Lexeme.DIV;
                result = new CheckedDivide(result, lowLevelParse());
            }
            skipWhitespaces();
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