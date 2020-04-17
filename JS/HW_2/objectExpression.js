"use strict";

function Const(val) {
    this.val = val;
}

Const.prototype = {
    evaluate: function () {
        return this.val;
    },
    diff: function () {
        return Const.ZERO;
    },
    toString: function () {
        return this.val.toString();
    },
    prefix: function () {
        return this.toString();
    },
    postfix : function () {
        return this.toString();
    }
}

function Variable(name) {
    this.name = name;
    this.index = vars[this.name] || 0;
}

Variable.prototype = {
    evaluate: function (...args) {
        return args[this.index];
    },
    diff: function (diffVar) {
        return diffVar === this.name ? Const.ONE : Const.ZERO;
    },
    toString: function () {
        return this.name.toString();
    },
    prefix: function () {
        return this.toString();
    },
    postfix : function () {
        return this.toString();
    }
}

const vars = {
    "x": 0,
    "y": 1,
    "z": 2
};

Const.ONE = new Const(1);
Const.ZERO = new Const(0);
Const.E = new Const(Math.E);

Variable.X = new Variable("x");
Variable.Y = new Variable("y");
Variable.Z = new Variable("z");

const consts = {
    "x": Variable.X,
    "y": Variable.Y,
    "z": Variable.Z,
    "e": Const.E
}
const operations = {};

function BaseOperation(...args) {
    this.args = args;
}

BaseOperation.prototype = {
    toString: function () {
        return [...this.args, this.operand].join(" ");
    },
    evaluate: function (...vars) {
        return this.operation(...this.args.map(a => a.evaluate(...vars)));
    },
    diff: function (diffVar) {
        return this.diffRule(this.args.map(a => a.diff(diffVar)), this.args);
    },
    joinArgs: function(f) {
        return `${[...this.args.map(f)].join(" ")}`;
    },
    prefix: function () {
        return `(${this.operand} ${this.joinArgs(a => a.prefix())})`;
    },
    postfix: function () {
        return `(${this.joinArgs(a => a.postfix())} ${this.operand})`;
    }
}

function StandardFunction(operation, operand, diff, arity) {
    const f = function(...args) {
        return BaseOperation.apply(this, args);
    }
    f.prototype = Object.create(BaseOperation.prototype);
    f.prototype.operation = operation;
    f.prototype.operand = operand;
    f.prototype.diffRule = diff;
    f.prototype.constructor = f;
    operations[operand] = f;
    f.arity = arity || operation.length;
    return f;
}

const Add = StandardFunction((a, b) => a + b, '+', ([dx, dy]) => new Add(dx, dy));
const Subtract = StandardFunction((a, b) => a - b, '-', ([dx, dy]) => new Subtract(dx, dy));

const Multiply = StandardFunction((a, b) => a * b, '*',
    ([dx, dy], [x, y]) => new Add(
        new Multiply(dx, y),
        new Multiply(x, dy)
    ));

const Divide = StandardFunction((a, b) => a / b, '/',
    ([dx, dy], [x, y]) => new Divide(
        new Subtract(
            new Multiply(dx, y),
            new Multiply(x, dy)
        ),
        new Multiply(y, y)
    ));

const Negate = StandardFunction(a => -a, 'negate', ([dx]) => new Negate(dx));

const Power = StandardFunction(Math.pow, 'pow',
    ([dx, dy], [x, y]) => new Multiply(
        new Power(x, y),
        new Add(
            new Multiply(dy, new Ln(x)),
            new Multiply(new Divide(y, x), dx)
        )
    ));

const Log = StandardFunction((a, b) => Math.log(Math.abs(b)) / Math.log(Math.abs(a)), 'log',
    ([dx, dy], [x, y]) => new Divide(
        new Subtract(
            new Multiply(
                new Divide(
                    dy, y
                ),
                new Ln(x)
            ),
            new Multiply(
                new Ln(y),
                new Divide(
                    dx, x
                )
            )
        ),
        new Multiply(
            new Ln(x), new Ln(x)
        )
    ));

const diffExp = (dx, x) => new Multiply(new Exp(x), dx);

const Ln = StandardFunction(a => Math.log(Math.abs(a)), 'ln', ([dx], [x]) => new Divide(dx, x));
const Exp = StandardFunction(a => Math.pow(Math.E, a), 'exp', ([dx], [x]) => diffExp(dx, x));

const diffSumexp = function(diffArgs, args) {
    if (args.length === 0) {
        return this;
    }
    let result = diffExp(diffArgs[0], args[0]);
    for (let i = 1; i < args.length; i++) {
        result = new Add(result, diffExp(diffArgs[i], args[i]));
    }
    return result;
}

Math.sumexp = (...args) => args.reduce((a, b) => a + Math.pow(Math.E, b), 0);
const Sumexp = StandardFunction(Math.sumexp, 'sumexp', diffSumexp);
const Softmax = StandardFunction((...args) => Math.pow(Math.E, args[0]) / Math.sumexp(...args), 'softmax',
    (diffArgs, args) => {
    const f = new Multiply(diffExp(diffArgs[0], args[0]), new Sumexp(...args));
    const g = new Multiply(diffSumexp(diffArgs, args), new Exp(args[0]));
    return new Divide(
        new Subtract(
            f, g
        ),
        new Multiply(
            new Sumexp(...args),
            new Sumexp(...args)
        )
    )
});


// ------------------- Parse -------------------

const leftFoldParse = f => regExp => expression => expression.trim().split(regExp).reduce((a, b) => f(a, b), []).pop();

const postFixParse = leftFoldParse((stack, arg) => {
    const lexeme = operations[arg] || consts[arg] || new Const(+arg);
    stack.push(lexeme.arity === undefined ? lexeme : new lexeme(...stack.splice(-lexeme.arity)));
    return stack;
});

const parse = expression => postFixParse(/\s+/)(expression);

// ------------------- Prefix/Postfix -------------------

const ExpressionError = function (found, pos, input, message) {
    this.found = found;
    this.input = input;
    this.pos = pos;
    this.message = message + ` || Found "${found}" || Position: ${pos} || Input: ${input}`;
}

const StandardError = function (message) {
    let f = function (found, pos, input) {
        return ExpressionError.call(this, found, pos, input, message);
    }
    f.prototype = Object.create(Error.prototype);
    f.prototype.constructor = ExpressionError;
    return f;
}

const InvalidNumberOfArgumentsError = expected => StandardError(`Invalid number of arguments || Expected ${expected}`);
const InvalidArgumentError = StandardError("Invalid function argument");
const ParenthesisError = expected => StandardError(`Expected ${expected} parenthesis`);
const InvalidLexemeError = StandardError("Found unexpected token");
const InvalidOperatorError = StandardError("Invalid Operator form");

const expressionParser = (() => {
    let input;
    let position;

    function throwError(ErrorType, found, pos) {
        throw new ErrorType(found, pos || position, input);
    }

    function parseNumber(num) {
        if (isNaN(num) || num.length === 0) {
            throwError(InvalidLexemeError, num);
        }
        return new Const(+num);
    }

    function parseLexeme(lexeme) {
        return operations[lexeme] || consts[lexeme] || parseNumber(lexeme);
    }

    const parseExpression = (regExp, operationIndex, errorMessageFunction) => expression => {
        input = expression;
        position = 1;
        const parenthesisStack = [];
        const arr = expression.replace(/\(/g, ' ( ').replace(/\)/g, ' ) ').trim().split(regExp);
        const result = arr.reduce((queue, lexeme) => {
            if (lexeme === '(') {
                parenthesisStack.push(queue.length);
            } else if (lexeme === ')') {
                if (parenthesisStack.length === 0) {
                    throwError(ParenthesisError("opening"), ')');
                }
                const args = queue.splice(parenthesisStack.pop());
                const index = operationIndex(args);
                const Operation = args[index];
                if (Operation.arity === undefined) {
                    throwError(InvalidOperatorError, Operation, position - args.length);
                }
                args.splice(index, 1);
                if (args.length !== Operation.arity && Operation.arity !== 0) {
                    throwError(InvalidNumberOfArgumentsError(Operation.arity), args.map(errorMessageFunction), position - args.length + index);
                }
                queue.push(new Operation(...args.map((a, index) =>
                        a.arity !== undefined ? throwError(InvalidArgumentError, a.prototype.operand, position - args.length + index) : a
                )));
            } else {
                queue.push(parseLexeme(lexeme));
            }
            position++;
            return queue;
        }, []);
        if (parenthesisStack.length !== 0) {
            throwError(ParenthesisError("closing"), "");
        }
        if (result.length !== 1) {
            throwError(ParenthesisError("opening and closing"), "");
        }
        return result.pop();
    }

    const parsePrefix = parseExpression(/\s+/, () => 0, a => a.prefix());
    const parsePostfix = parseExpression(/\s+/, (args) => args.length - 1, a => a.postfix());

    return {
        parsePrefix: parsePrefix,
        parsePostfix: parsePostfix
    }
})();

const parsePostfix = expressionParser.parsePostfix;
const parsePrefix= expressionParser.parsePrefix;

// let println = function () {
//     for (let value of arguments) {
//         console.log(value);
//     }
// };
//
// let c = new Softmax(new Variable('x'), Variable.Y);
// let b = c.diff('x');
// println(b.evaluate(2, 2));