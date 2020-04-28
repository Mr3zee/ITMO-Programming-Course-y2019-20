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
    postfix: function () {
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
    postfix: function () {
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
    joinArgs: function (f) {
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
    const f = function (...args) {
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

const diffSumexp = function (diffArgs, args) {
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

const StandardError = function (found, pos, input, message) {
    this.found = found;
    this.input = input;
    this.pos = pos;
    this.message = message + ` || Found "${found}" || Position: ${pos} || Input: ${input}`;
}

const expressionError = function (message) {
    let f = function (found, pos, input) {
        return StandardError.call(this, found, pos, input, message);
    }
    f.prototype = Object.create(Error.prototype);
    f.prototype.constructor = StandardError;
    return f;
}

const InvalidNumberOfArgumentsError = expected => expressionError(`Invalid number of arguments || Expected ${expected}`);
const InvalidArgumentError = expressionError("Invalid function argument");
const ParenthesisError = expected => expressionError(`Expected ${expected} parenthesis`);
const UnexpectedSymbolError = expressionError("Found unexpected token");

const Source = function (input, specSymbolsRegExp) {
    this.input = input;
    this.pos = 0;
    this.cur = input.charAt(0);
    this.regExp = new RegExp(specSymbolsRegExp.source + '|' + '\\s+');
}

Source.prototype = {
    isWhitespace: function () {
        return /\s+/.test(this.cur);
    },
    isSpecSymbol: function () {
        return this.regExp.test(this.cur);
    },
    hasNext: function () {
        return this.pos < this.input.length;
    },
    next: function () {
        this.cur = this.input.charAt(++this.pos);
    },
    skipWhitespaces: function () {
        while (this.isWhitespace()) {
            this.next();
        }
    },
    takeWord: function () {
        let retval = {pos: this.pos, word: ""};
        while (this.hasNext() && !this.isSpecSymbol()) {
            retval.word += this.cur;
            this.next();
        }
        this.skipWhitespaces();
        return retval;
    }
}

const expressionParser = (() => {
    let source;
    let operationRule;

    function skip() { source.next(); source.skipWhitespaces();}

    function throwError(ErrorType, found, position) {
        throw new ErrorType(found, position !== undefined ? position : source.pos, source.input);
    }

    function parseOperation(acc) {
        const index = operationRule(acc);
        return acc.splice(index, 1).pop().word;
    }

    function parseNumber(found) {
        const num = found.word;
        if (isNaN(num) || num.length === 0) {
            throwError(UnexpectedSymbolError, num, found.pos);
        }
        return new Const(+num);
    }

    function parseConst(lex) {
        return consts[lex.word] || parseNumber(lex);
    }

    function parseLexeme() {
        const lexeme = source.takeWord();
        lexeme.word = operations[lexeme.word] || parseConst(lexeme);
        return lexeme;
    }

    function functionParser() {
        skip();
        let acc = [];
        while (source.hasNext() && source.cur !== ')') {
            acc.push(source.cur === '(' ? (() => {
                const retval = functionParser(), pos = source.pos;
                checkParenthesis();
                return {word: retval, pos: pos};
            })() : parseLexeme());
        }
        const Constructor = parseOperation(acc);
        return (Constructor.arity !== 0 && acc.length !== Constructor.arity) ?
            throwError(InvalidNumberOfArgumentsError(Constructor.arity), acc.length) :
            new Constructor(...acc.map(a => {
                const lexeme = a.word;
                return lexeme.length === undefined ? lexeme : throwError(InvalidArgumentError, lexeme, lexeme.prototype.operand);
            }));
    }

    function checkParenthesis() {
        if (source.cur !== ')') {
            throwError(ParenthesisError('closing'), source.cur);
        }
        skip();
    }

    function parse(rule) {
        return (expression) => {
            operationRule = rule;
            source = new Source(expression.trim(), /[()]/);
            let retval = source.cur === '(' ? (() => {
                const a = functionParser();
                checkParenthesis();
                return a;
            })() : parseConst(source.takeWord());
            if (source.hasNext()) {
                const found = source.takeWord();
                throwError(UnexpectedSymbolError, found.word, found.pos);
            }
            return retval;
        }
    }

    const parsePostfix = parse((acc) => acc.length - 1);
    const parsePrefix = parse(() => 0);
    return {
        parsePostfix: parsePostfix,
        parsePrefix: parsePrefix
    }
})();

const parsePrefix = expressionParser.parsePrefix;
const parsePostfix = expressionParser.parsePostfix;

// let println = function () {
//     for (let value of arguments) {
//         console.log(value);
//     }
// };
//
// let a = "(3 2 +)";
// let b = parsePostfix(a);
// println(b.postfix());