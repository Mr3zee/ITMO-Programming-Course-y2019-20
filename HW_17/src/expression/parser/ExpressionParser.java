package expression.parser;

import expression.*;
import expression.exceptions.*;
import expression.operations.*;
import expression.type.*;

import java.util.Set;
import java.util.function.Function;

public class ExpressionParser<T extends Number> extends BaseParser implements Parser<T> {
    private final Set<Character> BINARY_END_OPS = Set.of('*', '/', '\0', ')');
    private final Function<String, EType<T>> parseEType;
    private Lexeme lastLexeme;

    public ExpressionParser(Function<String, EType<T>> parseEType) {
        super(Set.of ('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'x', 'y', 'z', '+', '-', '*', '/', '(', ')', '\0'));
        this.parseEType = parseEType;
    }

    @Override
    public CommonExpression<T> parse(final String expression) throws ParsingExpressionException {
        source = new ExpressionSource(expression);
        nextChar();
        skipWhitespaces();
        if (!hasNext()) {
            throw new EmptyExpressionPEException();
        }
        lastLexeme = Lexeme.START;
        CommonExpression<T> result = expressionParse();
        if (hasNext()) {
            throw new NoParenthesisPEException("opening", getNext());
        }
        return result;
    }


    private CommonExpression<T> expressionParse() throws ParsingExpressionException {
        CommonExpression<T> result = additiveParse();
        while (test('+', '-')) {
            if (compare('+')) {
                lastLexeme = Lexeme.PLUS;
                result = new Add<>(result, additiveParse());
            } else if (compare('-')) {
                lastLexeme = Lexeme.MINUS;
                result = new Subtract<>(result, additiveParse());
            }
        }
        if (!BINARY_END_OPS.contains(getCurrentLex())) {
            throw missingLexemeHandler();
        }
        return result;
    }

    private CommonExpression<T> additiveParse() throws ParsingExpressionException {
        CommonExpression<T> result = lowLevelParse();
        skipWhitespaces();
        while (test('*', '/')) {
            if (compare('*')) {
                lastLexeme = Lexeme.MULT;
                result = new Multiply<>(result, lowLevelParse());
            } else if (compare('/')) {
                lastLexeme = Lexeme.DIV;
                result = new Divide<>(result, lowLevelParse());
            }
            skipWhitespaces();
        }
        return result;
    }

    private CommonExpression<T> lowLevelParse() throws ParsingExpressionException {
        skipWhitespaces();
        if (compare('(')) {
            lastLexeme = Lexeme.OPAR;
            CommonExpression<T> result = expressionParse();
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
            return new Variable<>(name);
        } else if (isDigit()) {
            lastLexeme = Lexeme.NUM;
            return parseNumber(takeNumber());
        } else if (compare('-')) {
            if (isDigit()) {
                lastLexeme = Lexeme.NUM;
                return parseNumber("-" + takeNumber());
            }
            lastLexeme = Lexeme.MINUS;
            return new Negate<>(lowLevelParse());
        }
        throw missingLexemeHandler();
    }

    private CommonExpression<T> parseNumber(final String number) throws ParsingExpressionException {
        try {
            return new Const<>(parseEType.apply(number));
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