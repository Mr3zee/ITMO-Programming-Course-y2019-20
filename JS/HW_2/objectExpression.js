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
const e = new Const(Math.E);

const consts = {
    "x": X,
    "y": Y,
    "z": Z,
    "e": e
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
    f.prototype.diffRule = diff;
    f.prototype.constructor = f;
    operations[operand] = f;
    f.arity = arity === undefined ? operation.length : arity;
    return f;
}

const diffAddSub = function (differential, ...args) {
    return new this.constructor(...Array.from(args, a => a.diff(differential)));
}

const diffMultiplyDivide = function (resultFunction) {
    return (differential, ...args) => {
        const f = args.length === 2 ? args[0] : new this.constructor(...args.slice(-1));
        const g = args[args.length - 1];
        const fg = new Multiply(f.diff(differential), g);
        const gf = new Multiply(g.diff(differential), f);
        return resultFunction(fg, gf, f, g);
    }
}

const diffMultiply = diffMultiplyDivide((fg, gf) => new Add(fg, gf));
const diffDivide = diffMultiplyDivide((fg, gf, f, g) => new Divide(new Subtract(fg, gf), new Multiply(g, g)));

const diffPower = function (differential, ...args) {
    const f = args.length === 2 ? args[1] : new Power(...args.slice(1));
    const g = new Log(e, args[0]);
    const fg = new Multiply(f, g);
    const gf = new Power(args[0], f);
    return new Multiply(gf, fg.diff(differential));
}

const diffLog = function (differential, ...args) {
    const f = new Divide(...args.map(a => new Ln(a)).reverse());
    return f.diff(differential);
}

const Add = StandardFunction((a, b) => a + b, '+', diffAddSub);
const Subtract = StandardFunction((a, b) => a - b, '-', diffAddSub);
const Multiply = StandardFunction((a, b) => a * b, '*', diffMultiply);
const Divide = StandardFunction((a, b) => a / b, '/', diffDivide);
const Negate = StandardFunction(a => -a, 'negate', diffAddSub);
const Power = StandardFunction(Math.pow, 'pow', diffPower);
const Log = StandardFunction((a, b) => Math.log(Math.abs(b)) / Math.log(Math.abs(a)), 'log', diffLog);

const diffUnaryFunctions = function (makeFirst) {
    return (differential, arg) => {
        const f = makeFirst(arg);
        const g = arg.diff(differential);
        return new Multiply(f, g);
    }
}

const diffLn = diffUnaryFunctions((arg) => new Divide(ONE, arg));
const diffExp = diffUnaryFunctions((arg) => new Power(e, arg));
const diffCosh = diffUnaryFunctions((arg) => new Sinh(arg));
const diffSinh = diffUnaryFunctions((arg) => new Cosh(arg));

const Ln = StandardFunction(a => Math.log(Math.abs(a)), 'ln', diffLn);
const Exp = StandardFunction(a => Math.pow(Math.E, a), 'exp', diffExp);
const Sinh = StandardFunction(Math.sinh, 'sinh', diffSinh);
const Cosh = StandardFunction(Math.cosh, 'cosh', diffCosh);

const diffSumexp = function (differential, ...args) {
    const f = args.length === 1 ? new Exp(args[0]) : new Add(...args.map((a) => new Exp(a)));
    return f.diff(differential);
}
const diffSoftmax = function (differential, ...args) {
    const f = new Divide(new Exp(args[0]), new Sumexp(...args.map((a) => new Exp(a))));
    return f.diff(differential);
}

Math.sumexp = (...args) => args.reduce((a, b) => a + Math.pow(Math.E, b), 0);
const Sumexp = StandardFunction(Math.sumexp, 'sumexp', diffSumexp);
const Softmax = StandardFunction((...args) => Math.pow(Math.E, args[0]) / Math.sumexp(...args), 'softmax', diffSoftmax);

const leftFoldParse = f => regExp => expression => expression.trim().split(regExp).reduce((a, b) => f(a, b), []).pop();

const postFixParse = leftFoldParse((stack, arg) => {
    const lexeme = operations[arg] || consts[arg] || new Const(+arg);
    stack.push(lexeme.arity === undefined ? lexeme : new lexeme(...stack.splice(-lexeme.arity)));
    return stack;
});

const parse = expression => postFixParse(/\s+/)(expression);


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