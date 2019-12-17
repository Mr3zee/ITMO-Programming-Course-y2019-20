package expression.parser;

import expression.*;

import java.util.Map;

public class ExpressionParser extends BaseParser implements Parser {
    private boolean showLog;
    private final Map<Character, Priority> OPERATIONS = Map.of(
            '+', Priority.ADD,
            '-', Priority.SUB,
            '*', Priority.MUL,
            '/', Priority.DIV,
            ')', Priority.PAR,
            '(', Priority.PAR,
            '\0', Priority.END,
            '<', Priority.SHIFT,
            '>', Priority.SHIFT);

    @Override
    public CommonExpression parse(String data) {
        source = new ExpressionSource(data);
        nextChar();
        showLog = false;
        return parseNext(Priority.PAR);
    }

    private CommonExpression parseNext(Priority priority) {
        showLog("parseNext\n");
        CommonExpression exp = parseElement(priority);
        while (nextHasGreaterPriority(priority)) {
            exp = parseOperations(exp);
        }
        return exp;
    }

    private CommonExpression parseElement(Priority priority) {
        skipWhitespaces();
        showLog("parseElement\n");
        if (compare('(')) {
            return parseExpresion(priority);
        } else if (isConst()) {
            return parseConst(priority);
        } else if (isString()) {
            return parseString(priority);
        } else if (compare('-')) {
            return parseUnaryMinus();
        }
        throw error("Invalid Expression Symbol");
    }


    private CommonExpression parseExpresion(Priority priority) {
        showLog("parseExpression\n");
        CommonExpression exp = parseElement(priority);
        while (!compare(')')) {
            if (compare('\0')) throw error("Expected closing parenthesis");
            exp = parseOperations(exp);
        }
        return ifTakeNext(exp, priority);
    }

    private CommonExpression parseConst(Priority priority) {
        showLog("parseConst\n");
        return ifTakeNext(new Const(nextConst(false)), priority);
    }

    private CommonExpression parseString(Priority priority) {
        showLog("parseExpression\n");
        String name = next();
        if (name.equals("abs")) {
            return parseAbs();
        }
        if (name.equals("square")) {
            return parseSquare();
        }
        return parseVar(name, priority);
    }

    private CommonExpression parseVar(String name, Priority priority) {
        showLog("parseVar\n");
        return ifTakeNext(new Variable(name), priority);
    }

    private CommonExpression ifTakeNext(CommonExpression exp, Priority priority) {
        return nextHasGreaterPriority(priority) ? parseOperations(exp) : exp;
    }

    private boolean nextHasGreaterPriority(Priority priority) {
        skipWhitespaces();
        if (OPERATIONS.containsKey(current))
            return priority.getPriority() < OPERATIONS.get(current).getPriority();
        throw error("Invalid Operation symbol: " + current);
    }

    private CommonExpression parseUnaryMinus() {
        showLog("parseUnaryMinus\n");
        skipWhitespaces();
        int count = 1;
        while (compare('-')) {
            count++;
            skipWhitespaces();
        }
        if (count % 2 == 0) {
            return parseElement(Priority.MINUS);
        }
        if (isConst()) {
            return new Const(nextConst(true));
        }
        return new UnaryMinus(parseElement(Priority.MINUS));
    }

    private CommonExpression parseAbs() {
        showLog("parseAbs\n");
        return new Abs(parseElement(Priority.ABS));
    }

    private CommonExpression parseSquare() {
        showLog("parseSquare\n");
        return new Square(parseElement(Priority.SQUARE));
    }

    private CommonExpression parseOperations(CommonExpression first) {
        showLog("parseOperations\n");
        skipWhitespaces();
        if (compare('+')) {
            return parseAdd(first);
        } else if (compare('-')){
            return parseSubtract(first);
        } else if (compare('*')) {
            return parseMultiply(first);
        } else if (compare('/')) {
            return parseDivide(first);
        } else if (compare('<')) {
            expect('<');
            return parseLeftShift(first);
        } else if (compare('>')) {
            expect('>');
            return parseRightShift(first);
        }
        throw error("Invalid Operation symbol: " + current);
    }

    private CommonExpression parseRightShift(CommonExpression first) {
        showLog("parseRightShift\n");
        return new RightShift(first, parseNext(Priority.SHIFT));
    }

    private CommonExpression parseLeftShift(CommonExpression first) {
        showLog("parseLeftShift\n");
        return new LeftShift(first, parseNext(Priority.SHIFT));
    }

    private CommonExpression parseAdd(CommonExpression first) {
        showLog("parseAdd\n");
        return new Add(first, parseNext(Priority.ADD));
    }

    private CommonExpression parseSubtract(CommonExpression first) {
        showLog("parseSubtract\n");
        return new Subtract(first, parseNext(Priority.SUB));
    }

    private CommonExpression parseMultiply(CommonExpression first) {
        showLog("parseMultiply\n");
        return new Multiply(first, parseNext(Priority.MUL));
    }

    private CommonExpression parseDivide(CommonExpression first) {
        showLog("parseDivide\n");
        return new Divide(first, parseNext(Priority.DIV));
    }

    private String next() {
        skipWhitespaces();
        StringBuilder next = new StringBuilder();
        while (!(Character.isWhitespace(current) || OPERATIONS.containsKey(current))) {
            next.append(current);
            nextChar();
        }
        return next.toString();
    }

    private int nextConst(final boolean negative) {
        try {
            return Integer.parseInt((negative ? "-" : "") + next());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw error("Invalid variable name");
        }
    }

    private boolean isConst() {
        return between('0', '9');
    }

    private boolean isString() {
        return between('a', 'z') || between('A', 'Z');
    }

    private boolean between(char ch1, char ch2) {
        return ch1 <= current && current <= ch2;
    }

    private void showLog(String message) {
        if (showLog) {
            System.out.print(message);
        }
    }
}
