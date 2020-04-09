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
    diff: function () {
        return new Const(0);
    }
}

function Variable(name) {
    this.name = name;
}

Variable.prototype = {
    toString: function () {
        return this.name.toString();
    },
    evaluate: function (...args) {
        return args[vars[this.name] || 0];
    },
    diff: function (differential) {
        return differential === this.name ? new Const(1) : new Const(0);
    }
}

const vars = {
    "x": 0,
    "y": 1,
    "z": 2
};

const X = new Variable("x");
const Y = new Variable("y");
const Z = new Variable("z");
const e = new Const(Math.E);

const lexemes = {
    "x": X,
    "y": Y,
    "z": Z
};

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
        return this.diffRule(this.args, differential);
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
    lexemes[operand] = f;
    f.arity = arity === undefined ? operation.length : arity;
    return f;
}

const diffAddSub = function (args, differential) {
    args = args.reduce((a, b) => {
        a.push(b.diff(differential));
        return a
    }, []);
    return new this.constructor(...args);
}

const diffMultiplyDivide = function (resultFunction) {
    return (args, differential) => {
        const f = args.length === 2 ? args[0] : new this.constructor(...args.slice(-1));
        const g = args[args.length - 1];
        const fg = new Multiply(f.diff(differential), g);
        const gf = new Multiply(g.diff(differential), f);
        return resultFunction(fg, gf, f, g);
    }
}

const diffMultiply = diffMultiplyDivide((fg, gf) => new Add(fg, gf));
const diffDivide = diffMultiplyDivide((fg, gf, f, g) => new Divide(new Subtract(fg, gf), new Multiply(g, g)));

const diffPower = function (args, differential) {
    const f = args.length === 2 ? args[1] : new Power(...args.slice(1));
    const g = new Log(e, args[0]);
    const fg = new Multiply(f, g);
    const gf = new Power(args[0], f);
    return new Multiply(gf, fg.diff(differential));
}

const diffLog = function (args, differential) {
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
    return (arg, differential) => {
        const f = makeFirst(arg);
        const g = arg[0].diff(differential);
        return new Multiply(f, g);
    }
}

const diffLn = diffUnaryFunctions((arg) => new Divide(new Const(1), arg[0]));
const diffExp = diffUnaryFunctions((arg) => new Power(e, arg[0]));
const diffCosh = diffUnaryFunctions((arg) => new Sinh(...arg));
const diffSinh = diffUnaryFunctions((arg) => new Cosh(...arg));

const Ln = StandardFunction(a => Math.log(Math.abs(a)), 'ln', diffLn);
const Exponent = StandardFunction(a => Math.pow(Math.E, a), 'exp', diffExp);
const Sinh = StandardFunction(Math.sinh, 'sinh', diffSinh);
const Cosh = StandardFunction(Math.cosh, 'cosh', diffCosh);

const foldParse = f => regExp => expression => expression.trim().split(regExp).reduce((a, b) => f(a, b), []).pop();

const postFixParse = foldParse((stack, arg) => {
    const lexeme = lexemes[arg] || new Const(+arg);
    stack.push(lexeme.arity === undefined ? lexeme : new lexeme(...stack.splice(-lexeme.arity)));
    return stack;
});

const parse = expression => postFixParse(/\s+/)(expression);


// let println = function () {
//     for (let value of arguments) {
//         console.log(value);
//     }
// };
//
// let a = new Add(new Variable('x'), new Const(2));
// let c = new a.constructor(1, 2);
// let d = c.toString();
// let b = a.diff("x");
// println(b.toString());