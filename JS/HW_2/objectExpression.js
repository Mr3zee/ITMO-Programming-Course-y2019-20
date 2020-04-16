"use strict";

function Const(val) {
    this.val = val;
}

Const.prototype = {
    evaluate: function () {
        return this.val;
    },
    diff: function () {
        return ZERO;
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
    diff: function (differential) {
        return differential === this.name ? ONE : ZERO;
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

const ONE = new Const(1);
const ZERO = new Const(0);
const X = new Variable("x");
const Y = new Variable("y");
const Z = new Variable("z");
const E = new Const(Math.E);
const epsilon = 1e-7;
const Epsilon = new Const(epsilon);
const DoubleEpsilon = new Const(2 * epsilon);

const consts = {
    "x": X,
    "y": Y,
    "z": Z,
    "e": E
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
    diff: function (differential) {
        return this.diffRule(differential, ...this.args);
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
    f.prototype.diffRule = diff === undefined ? diffStandard : diff;
    f.prototype.constructor = f;
    operations[operand] = f;
    f.arity = arity === undefined ? operation.length : arity;
    return f;
}

const diffAddSub = function (differential) {
    return new this.constructor(...Array.from(this.args, a => a.diff(differential)));
}

const changeVar = (a, Constructor, differential) => a.name === differential ? new Constructor(a, Epsilon) : changeArgs(a, Constructor, differential);

const changeArgs = (func, Constructor, differential) => func.args === undefined ? func :
    new func.constructor(...Array.from(func.args, a => changeVar(a, Constructor, differential)));

const diffStandard = function (differential) {
    const f = changeArgs(this, Add, differential);
    const g = changeArgs(this, Subtract, differential);
    return new Divide(new Subtract(f, g), DoubleEpsilon);
}

const Add = StandardFunction((a, b) => a + b, '+', diffAddSub);
const Subtract = StandardFunction((a, b) => a - b, '-', diffAddSub);
const Multiply = StandardFunction((a, b) => a * b, '*');
const Divide = StandardFunction((a, b) => a / b, '/');
const Negate = StandardFunction(a => -a, 'negate');
const Power = StandardFunction(Math.pow, 'pow');
const Log = StandardFunction((a, b) => Math.log(Math.abs(b)) / Math.log(Math.abs(a)), 'log');

const Ln = StandardFunction(a => Math.log(Math.abs(a)), 'ln');
const Exp = StandardFunction(a => Math.pow(Math.E, a), 'exp');
const Sinh = StandardFunction(Math.sinh, 'sinh');
const Cosh = StandardFunction(Math.cosh, 'cosh');

Math.sumexp = (...args) => args.reduce((a, b) => a + Math.pow(Math.E, b), 0);
const Sumexp = StandardFunction(Math.sumexp, 'sumexp');
const Softmax = StandardFunction((...args) => Math.pow(Math.E, args[0]) / Math.sumexp(...args), 'softmax');


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
        throw new ErrorType(found, pos === undefined ? position : pos, input);
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
// let c = "((3 4 -) 2 3 +)";
// let b = parsePostfix(c);
// println(b.prefix());