package expression.parser;

import expression.*;

import java.util.Map;

public class ExpressionParser extends BaseParser implements Parser {
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
        return parseNext(Priority.END);
    }

    private CommonExpression parseNext(Priority priority) {
        CommonExpression exp = parseElement(priority);
        while (nextHasGreaterPriority(priority)) {
            exp = parseOperations(exp);
        }
        return exp;
    }

    private CommonExpression parseElement(Priority priority) {
        skipWhitespaces();
        if (compare('(')) {
            CommonExpression exp = parseNext(Priority.PAR);
            expect(')');
            return exp;
        } else if (isConst()) {
            return ifTakeNext(new Const(nextConst(false)), priority);
        } else if (isString()) {
            return parseString(priority);
        } else if (compare('-')) {
            return parseUnaryMinus();
        }
        throw error("Invalid Expression Symbol");
    }

    private CommonExpression parseString(Priority priority) {
        String name = next();
        if (name.equals("abs")) {
            return new Abs(parseElement(Priority.ABS));
        }
        if (name.equals("square")) {
            return new Square(parseElement(Priority.SQUARE));
        }
        return ifTakeNext(new Variable(name), priority);
    }

    private CommonExpression ifTakeNext(CommonExpression exp, Priority priority) {
        return nextHasGreaterPriority(priority) ? parseOperations(exp) : exp;
    }

    private boolean nextHasGreaterPriority(Priority priority) {
        skipWhitespaces();
        Priority nextPriority = OPERATIONS.getOrDefault(current, null);
        if (nextPriority != null) {
            return priority.getPriority() < nextPriority.getPriority();
        }
        throw error("Invalid Operation symbol: " + current);
    }

    private CommonExpression parseUnaryMinus() {
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

    private CommonExpression parseOperations(CommonExpression first) {
        skipWhitespaces();
        if (compare('+')) {
            return new Add(first, parseNext(Priority.ADD));
        } else if (compare('-')){
            return new Subtract(first, parseNext(Priority.SUB));
        } else if (compare('*')) {
            return new Multiply(first, parseNext(Priority.MUL));
        } else if (compare('/')) {
            return new Divide(first, parseNext(Priority.DIV));
        } else if (compare('<')) {
            expect('<');
            return new LeftShift(first, parseNext(Priority.SHIFT));
        } else if (compare('>')) {
            expect('>');
            return new RightShift(first, parseNext(Priority.SHIFT));
        }
        throw error("Invalid Operation symbol: " + current);
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
            throw error("Invalid Const");
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
}
