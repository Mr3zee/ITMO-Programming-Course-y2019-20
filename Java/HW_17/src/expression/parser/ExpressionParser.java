package expression.parser;

import expression.*;
import expression.exceptions.*;
import expression.operations.*;
import expression.type.*;

import java.util.*;
import java.util.function.Function;

public class ExpressionParser<T extends Number> extends BaseParser implements MyParser<T> {
    private final Function<String, EType<T>> parseEType;
    private Lexeme lastLexeme;

    public ExpressionParser(Function<String, EType<T>> parseEType) {
        super(Set.of ('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'x', 'y', 'z', '+', '-', '*', '/', '(', ')', '\0'),
                Map.of("count", Lexeme.CNT, "min", Lexeme.MIN, "max", Lexeme.MAX));
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
        CommonExpression<T> result = minMaxParse();
        if (hasNext()) {
            throw new ParenthesisAbsencePEException("opening", getExceptionParameters());
        }
        return result;
    }

    private CommonExpression<T> minMaxParse() throws ParsingExpressionException {
        CommonExpression<T> result = additiveParse();
        while (compare("min", "max")) {
            if (compareAndSkip("min")) {
                lastLexeme = Lexeme.MIN;
                result = new Minimum<>(result, additiveParse());
            } else if (compareAndSkip("max")) {
                lastLexeme = Lexeme.MAX;
                result = new Maximum<>(result, additiveParse());
            }
        }
        if (compare("\0", ")")) {
            return result;
        }
        throw missingLexemeOrIllegalSymbolException(getExceptionParameters());
    }

    private CommonExpression<T> additiveParse() throws ParsingExpressionException {
        CommonExpression<T> result = multiplicativeParse();
        while (compare("+", "-")) {
            if (compareAndSkip("+")) {
                lastLexeme = Lexeme.ADD;
                result = new Add<>(result, multiplicativeParse());
            } else if (compareAndSkip("-")) {
                lastLexeme = Lexeme.SUB;
                result = new Subtract<>(result, multiplicativeParse());
            }
        }
        return result;
    }

    private CommonExpression<T> multiplicativeParse() throws ParsingExpressionException {
        CommonExpression<T> result = lowLevelParse();
        skipWhitespaces();
        while (compare("*", "/")) {
            if (compareAndSkip("*")) {
                lastLexeme = Lexeme.MULT;
                result = new Multiply<>(result, lowLevelParse());
            } else if (compareAndSkip("/")) {
                lastLexeme = Lexeme.DIV;
                result = new Divide<>(result, lowLevelParse());
            }
            skipWhitespaces();
        }
        return result;
    }

    private CommonExpression<T> lowLevelParse() throws ParsingExpressionException {
        skipWhitespaces();
        if (compareAndSkip("(")) {
            lastLexeme = Lexeme.OPAR;
            CommonExpression<T> result = minMaxParse();
            if (compareAndSkip(")")) {
                lastLexeme = Lexeme.CPAR;
                return result;
            }
            throw new ParenthesisAbsencePEException("closing", getExceptionParameters());
        } else if (compare("x", "y", "z")) {
            char c = getCurrentLex();
            lastLexeme = c == 'x' ? Lexeme.X : c == 'y' ? Lexeme.Y : Lexeme.Z;
            String name = String.valueOf(c);
            nextChar();
            return new Variable<>(name);
        } else if (isDigit()) {
            lastLexeme = Lexeme.NUM;
            return parseNumber(takeNumber());
        } else if (compareAndSkip("-")) {
            if (isDigit()) {
                lastLexeme = Lexeme.NUM;
                return parseNumber("-" + takeNumber());
            }
            lastLexeme = Lexeme.SUB;
            return new Negate<>(lowLevelParse());
        } else {
            String word = getCheckedWord();
            return expressionWrapper(word);
        }
    }

    private String getCheckedWord() {
        if (LEXEMES.contains(getCurrentLex())) {
            throw missingLexemeOrIllegalSymbolException(getExceptionParameters());
        }
        String word = takeUnaryOpWord();
        Lexeme lexeme = WORDS.get(word);
        if (lexeme == null || lexeme.getArity() != 1) {
            throw missingLexemeOrIllegalSymbolException(getExceptionParameters(word, source.getPosition() - word.length() - 1));
        }
        lastLexeme = lexeme;
        checkAfterWordSymbol();
        return word;
    }

    private void checkAfterWordSymbol() {
        if (!(Character.isWhitespace(getCurrentLex()) || compare("-", "(", "\0"))) {
            throw new MissingWhitespacePEException(lastLexeme.getName(), getExceptionParameters());
        }
    }

    private CommonExpression<T> expressionWrapper(String word) {
        CommonExpression<T> nextExpression = lowLevelParse();
        return switch (word) {
            case "count" -> new Count<>(nextExpression);
            default -> null;
        };
    }

    private CommonExpression<T> parseNumber(final String number) throws ParsingExpressionException {
        try {
            return new Const<>(parseEType.apply(number));
        } catch (NumberFormatException e) {
            int position = source.getPosition() - number.length() - 1;
            if (checkDouble(number)) {
                throw new UnsupportedCastPEException(getExceptionParameters(number, position));
            }
            if (number.charAt(0) == '-') {
                throw new ConstantUnderflowPEException(getExceptionParameters(number, position));
            }
            throw new ConstantOverflowPEException(getExceptionParameters(number, position));
        }
    }

    private ParsingExpressionException missingLexemeOrIllegalSymbolException(ExceptionParameters nextWord) {
        if (findLexeme(nextWord.getWord())) {
            return new MissingLexemePEException(lastLexeme.getName(), nextWord);
        }
        return new IllegalSymbolPEException(nextWord);
    }
}