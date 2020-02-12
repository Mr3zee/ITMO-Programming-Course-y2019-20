package expression.parser;

import expression.*;

import java.util.Set;

public class ExpressionParser extends BaseParser implements Parser {
    private final Set<Character> OPERATIONS = Set.of ('+', '-', '*', '/', '(', ')', '<', '>');

    @Override
    public CommonExpression parse(String expression) {
        source = new ExpressionSource(expression);
        nextChar();
        return expressionParse();
    }

    private CommonExpression expressionParse() {
        CommonExpression result = shiftsParse();
        skipWhitespaces();
        while (isShiftOperation()) {
            if (compare("<<")) {
                result = new LeftShift(result, shiftsParse());
            } else if (compare(">>")) {
                result = new RightShift(result, shiftsParse());
            }
            skipWhitespaces();
        }
        return result;
    }


    private CommonExpression shiftsParse() {
        CommonExpression result = additiveParse();
        skipWhitespaces();
        while (isAdditiveOperation()) {
            if (compare('+')) {
                result = new Add(result, additiveParse());
            } else if (compare('-')) {
                result = new Subtract(result, additiveParse());
            }
            skipWhitespaces();
        }
        return result;
    }

    private CommonExpression additiveParse() {
        CommonExpression result = multiplicativeParse();
        skipWhitespaces();
        while (isMultiplicativeOperation()) {
            if (compare('*')) {
                result = new Multiply(result, multiplicativeParse());
            } else if (compare('/')) {
                result = new Divide(result, multiplicativeParse());
            }
            skipWhitespaces();
        }
        return result;
    }

    private CommonExpression multiplicativeParse() {
        skipWhitespaces();
        if (compare('(')) {
            CommonExpression result = expressionParse();
            expect(')');
            return result;
        } else if (compare("abs")) {
            return new Abs(multiplicativeParse());
        } else if (compare("square")) {
            return new Square(multiplicativeParse());
        } else if (isVariable()) {
            return new Variable(nextWord());
        } else if (isNumber()) {
            return parseNumber(nextWord());
        } else if (compare('-')) {
            if (isNumber()) {
                return parseNumber("-" + nextWord());
            }
            return new UnaryMinus(multiplicativeParse());
        }
        throw error("Invalid lexeme");
    }

    private CommonExpression parseNumber(String number) {
        try {
            int num = Integer.parseInt(number);
            return new Const(num);
        } catch (NumberFormatException e) {
            throw error("Invalid number format");
        }
    }

    private String nextWord() {
        StringBuilder word = new StringBuilder();
        while (!Character.isWhitespace(currentLex) && currentLex != '\0' && !OPERATIONS.contains(currentLex)) {
            word.append(currentLex);
            nextChar();
        }
        return word.toString();
    }

    private boolean isNumber() {
        return '0' <= currentLex && currentLex <= '9';
    }

    private boolean isVariable() {
        return currentLex == 'x' || currentLex == 'y' || currentLex == 'z';
    }

    private boolean isAdditiveOperation() {
        return currentLex == '+' || currentLex == '-';
    }

    private boolean isMultiplicativeOperation() {
        return currentLex == '*' || currentLex == '/';
    }

    private boolean isShiftOperation() {
        return currentLex == '<' || currentLex == '>';
    }
}
