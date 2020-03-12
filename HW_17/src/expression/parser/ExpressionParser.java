package expression.parser;

import expression.*;
import expression.exceptions.*;
import expression.operations.*;
import expression.type.*;

import java.util.*;
import java.util.function.Function;

public class ExpressionParser<T extends Number> extends BaseParser implements Parser<T> {
    private final Set<Character> BINARY_END_OPS = Set.of('*', '/', '\0', ')');
    private final Function<String, EType<T>> parseEType;
    private Lexeme lastLexeme;

    public ExpressionParser(Function<String, EType<T>> parseEType) {
        super(Set.of ('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'x', 'y', 'z', '+', '-', '*', '/', '(', ')', '\0'),
                Map.of("count", Lexeme.CNT));
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
        CommonExpression<T> result = additiveParse();
        if (hasNext()) {
            throw new NoParenthesisPEException("opening", getNext());
        }
        return result;
    }


    private CommonExpression<T> additiveParse() throws ParsingExpressionException {
        CommonExpression<T> result = multiplicativeParse();
        while (compare('+', '-')) {
            if (compareAndGetNext('+')) {
                lastLexeme = Lexeme.PLUS;
                result = new Add<>(result, multiplicativeParse());
            } else if (compareAndGetNext('-')) {
                lastLexeme = Lexeme.MINUS;
                result = new Subtract<>(result, multiplicativeParse());
            }
        }
        if (!BINARY_END_OPS.contains(getCurrentLex())) {
            throw missingLexemeOrIllegalSymbolException(getNext());
        }
        return result;
    }

    private CommonExpression<T> multiplicativeParse() throws ParsingExpressionException {
        CommonExpression<T> result = lowLevelParse();
        skipWhitespaces();
        while (compare('*', '/')) {
            if (compareAndGetNext('*')) {
                lastLexeme = Lexeme.MULT;
                result = new Multiply<>(result, lowLevelParse());
            } else if (compareAndGetNext('/')) {
                lastLexeme = Lexeme.DIV;
                result = new Divide<>(result, lowLevelParse());
            }
            skipWhitespaces();
        }
        return result;
    }

    private CommonExpression<T> lowLevelParse() throws ParsingExpressionException {
        skipWhitespaces();
        if (compareAndGetNext('(')) {
            lastLexeme = Lexeme.OPAR;
            CommonExpression<T> result = additiveParse();
            if (compareAndGetNext(')')) {
                lastLexeme = Lexeme.CPAR;
                return result;
            }
            throw new NoParenthesisPEException("closing", getNext());
        } else if (compare('x', 'y', 'z')) {
            char c = getCurrentLex();
            lastLexeme = c == 'x' ? Lexeme.X : c == 'y' ? Lexeme.Y : Lexeme.Z;
            String name = String.valueOf(c);
            nextChar();
            return new Variable<>(name);
        } else if (isDigit()) {
            lastLexeme = Lexeme.NUM;
            return parseNumber(takeNumber());
        } else if (compareAndGetNext('-')) {
            if (isDigit()) {
                lastLexeme = Lexeme.NUM;
                return parseNumber("-" + takeNumber());
            }
            lastLexeme = Lexeme.MINUS;
            return new Negate<>(lowLevelParse());
        } else {
            String word = getCheckedWord();
            return expressionWrapper(word);
        }
    }

    private String getCheckedWord() {
        String word = nextWord();
        Lexeme lexeme = WORDS.get(word);
        if (lexeme == null) {
            throw missingLexemeOrIllegalSymbolException(getNext(word, source.getPosition() - word.length() - 1));
        }
        lastLexeme = lexeme;
        checkAfterWordSymbol();
        return word;
    }

    private void checkAfterWordSymbol() {
        if (!(Character.isWhitespace(getCurrentLex()) || compare('-', '(', '\0'))) {
            throw new MissingWhitespacePEException(lastLexeme.getName(), getNext());
        }
    }

    private CommonExpression<T> expressionWrapper(String word) {
        CommonExpression<T> nextExpression = lowLevelParse();
        switch (word) {
            case "count":
                return new Count<>(nextExpression);
        }
        return null;
    }

    private CommonExpression<T> parseNumber(final String number) throws ParsingExpressionException {
        try {
            return new Const<>(parseEType.apply(number));
        } catch (NumberFormatException e) {
            int position = source.getPosition() - number.length() - 1;
            if (checkDouble(number)) {
                throw new UnsupportedCastPEException(getNext(number, position));
            }
            if (number.charAt(0) == '-') {
                throw new ConstantUnderflowPEException(getNext(number, position));
            }
            throw new ConstantOverflowPEException(getNext(number, position));
        }
    }

    private ParsingExpressionException missingLexemeOrIllegalSymbolException(NextWordParameters nextWord) {
        if (findLexeme(nextWord.getWord())) {
            return new MissingLexemePEException(lastLexeme.getName(), nextWord);
        }
        return new IllegalSymbolPEException(nextWord);
    }
}