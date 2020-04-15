"use strict";

function Const(val) {
    this.val = val;
}

Const.prototype = {
    evaluate: function () {
        return this.val;
    },
    toString: function () {
        return this.val.toString();
    },
    prefix: function () {
        return this.toString();
    },
    diff: function () {
        return ZERO;
    }
}

function Variable(name) {
    this.name = name;
    this.index = vars[this.name] || 0;
}

Variable.prototype = {
    toString: function () {
        return this.name.toString();
    },
    prefix: function () {
        return this.toString();
    },
    evaluate: function (...args) {
        return args[this.index];
    },
    diff: function (differential) {
        return differential === this.name ? ONE : ZERO;
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
    prefix: function () {
        if (this.args.length === 0) {
            return `(${this.operand} )`;
        }
        return `(${[this.operand, ...this.args.map(a => a.prefix())].join(" ")})`;
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

const leftFoldParse = f => regExp => expression => expression.trim().split(regExp).reduce((a, b) => f(a, b), []).pop();

const postFixParse = leftFoldParse((stack, arg) => {
    const lexeme = operations[arg] || consts[arg] || new Const(+arg);
    stack.push(lexeme.arity === undefined ? lexeme : new lexeme(...stack.splice(-lexeme.arity)));
    return stack;
});

const parse = expression => postFixParse(/\s+/)(expression);

// ------------------- Prefix -------------------

const isWhitespace = function (ch) {
    return /\s+/.test(ch);
}

const Source = function (expression) {
    this.expression = expression;
    this.current = expression.charAt(0);
    this.pos = 1;
}
Source.prototype = {
    getPosition: function () {
        return this.pos;
    },
    getExpression: function () {
        return this.expression;
    },
    nextChar: function () {
        this.current = this.expression.charAt(this.pos++);
    },
    hasNext: function () {
        return this.pos <= this.expression.length;
    },
    compare: function (test) {
        return this.current === test;
    },
    skipWhitespaces: function () {
        while (isWhitespace(this.current)) {
            this.nextChar();
        }
    },
    nextWord: function () {
        let result = "";
        while (this.hasNext() && !(isWhitespace(this.current) || this.current === ')' || this.current === '(')) {
            result += this.current;
            this.nextChar();
        }
        return result;
    },
    takeFrom: function (ch) {
        let i = this.pos - 1;
        while(this.expression.charAt(i) !== ch) {
            i--;
        }
        return this.expression.substr(i, this.pos - i);
    }
}

const ExpressionError = function (found, input, message) {
    this.found = found;
    this.input = input;
    this.message = message + ` || Found "${found}" || Input: ${input}`;
}

const StandardError = function (message, name) {
    let f = function (found, input) {
        return ExpressionError.call(this, found, input, message);
    }
    f.prototype = Object.create(Error.prototype);
    f.prototype.constructor = ExpressionError;
    f.prototype.name = name;
    return f;
}

const InvalidArgumentsError = StandardError("Invalid arguments/number of arguments", "InvalidArgumentsError");
const ParenthesisError = StandardError("Expected opening/closing parenthesis", "ParenthesisError");
const InvalidLexemeError = StandardError("Found unexpected token", "InvalidLexemeError");
const InvalidOperatorError = StandardError("Invalid Operator form", "InvalidOperatorError");

const parseTools = (() => {
    let source;
    let parsingRule;
    function throwError(error, found) {
        throw new error(found.toString(), source.getExpression());
    }
    function takeOperation() {
        return source.takeFrom('(');
    }
    function parseArguments() {
        const args = [];
        source.skipWhitespaces();
        while (!source.compare(")")) {
            args.push(parseExpression());
            source.skipWhitespaces();
        }
        return args;
    }
    function checkOperation(args, pos) {
        if (args[pos].arity === undefined) {
            throwError(InvalidOperatorError, takeOperation());
        }
        for (let i = 0; i < args.length; i++) {
            if (i !== pos && args[i].arity !== undefined) {
                throwError(InvalidArgumentsError, takeOperation());
            }
        }
    }
    function parseNumber(num) {
        if (isNaN(num) || num.length === 0) {
            throwError(InvalidLexemeError, num);
        }
        return new Const(+num);
    }
    function parseExpression() {
        source.skipWhitespaces();
        if (source.compare("(")) {
            source.nextChar();
            const args = parseArguments();
            const result = parsingRule(args);
            source.skipWhitespaces();
            if (source.compare(")")) {
                source.nextChar();
                return result;
            }
            throwError(ParenthesisError, source.nextWord());
        }
        const lex = source.nextWord();
        return operations[lex] || consts[lex] || parseNumber(lex);
    }
    const parseTemplate = function (expression) {
        source = new Source(expression.trim());
        const result = parseExpression();
        source.skipWhitespaces();
        if (source.hasNext()) {
            throwError(ParenthesisError, source.nextWord());
        }
        return result;
    };
    function insertArguments(Operation, args) {
        if (Operation.arity !== args.length && Operation.arity !== 0) {
            throwError(InvalidArgumentsError, takeOperation());
        }
        return new Operation(...args);
    }
    const parsePrefix = (expression) => {
        parsingRule = (args) => {
            checkOperation(args, 0);
            return insertArguments(args.shift(), args);
        }
        return parseTemplate(expression);
    }
    const parsePostfix = (expression) => {
        parsingRule = (args) => {
            checkOperation(args, args.length - 1);
            return insertArguments(args.pop(), args);
        }
        return parseTemplate(expression);
    }
    return {
        parsePrefix: parsePrefix,
        parsePostfix: parsePostfix
    }
})();

const parsePrefix = parseTools.parsePrefix;
const parsePostfix = parseTools.parsePostfix;

// let println = function () {
//     for (let value of arguments) {
//         console.log(value);
//     }
// };
//
// let c = new Multiply(X, X);
// let dz = new Multiply(new Const(5), new Multiply(Z, new Multiply(Z, Z)));
// let dx = new Multiply(new Const(5), new Multiply(X, new Multiply(X, X)));
// let f = new Multiply(Y, new Const(8));
// // let a = new Subtract(new Add(c, d), f);
// // let a = new Subtract(new Add(X, d), Y);
// let bx = dx.diff('x');
// let bz = dz.diff('z');
// println(bx.evaluate(2, 2, 2));
// println(bz.evaluate(2, 2, 2));
// println(X.name);