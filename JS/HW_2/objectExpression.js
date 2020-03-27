"use strict";

function Const(val) {
    this.toString = function () {
        return val.toString();
    }
    this.evaluate = function () {
        return val;
    }
    this.diff = function () {
        return new Const(0);
    }
}

function Variable(name) {
    this.toString = function () {
        return name;
    }
    this.evaluate = function (...args) {
        return args[Vars[name] || 0];
    }
    this.diff = function (differential) {
        return differential === name ? new Const(1) : new Const(0);
    }
}

const Vars = {
    "x": 0,
    "y": 1,
    "z": 2
};
const X = new Variable("x");
const Y = new Variable("y");
const Z = new Variable("z");
const E = new Const(Math.E);

function StandardFunction(operation, arity, operand, diff) {
    const f = function (...args) {
        this.toString = function () {
            return args.reduce((a, b) => a + " " + b) + " " + operand;
        }
        this.evaluate = function (...vars) {
            return operation(...args.map(a => a.evaluate(...vars)));
        }
        this.diff = function (differential) {
            return diff(args, differential, this.constructor);
        }
    }
    f.arity = arity;
    return f;
}

const diffAddSub = function (args, differential, Constructor) {
    args = args.reduce((a, b) => {
        a.push(b.diff(differential));
        return a
    }, []);
    return new Constructor(...args);
}

const diffMultiplyDivide = function (resultFunction) {
    return (args, differential, Constructor) => {
        const f = args.length === 2 ? args[0] : new Constructor(...args.slice(-1));
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
    const g = new Log(E, args[0]);
    const fg = new Multiply(f, g);
    const gf = new Power(args[0], f);
    return new Multiply(gf, fg.diff(differential));
}

const diffLog = function (args, differential) {
    const f = new Divide(...args.map(a => new Ln(a)).reverse());
    return f.diff(differential);
}

const Add = StandardFunction((a, b) => a + b, 2, '+', diffAddSub);
const Subtract = StandardFunction((a, b) => a - b, 2, '-', diffAddSub);
const Multiply = StandardFunction((a, b) => a * b, 2, '*', diffMultiply);
const Divide = StandardFunction((a, b) => a / b, 2, '/', diffDivide);
const Negate = StandardFunction(a => -a, 1, 'negate', diffAddSub);
const Power = StandardFunction(Math.pow, 2, 'pow', diffPower);
const Log = StandardFunction((a, b) => Math.log(Math.abs(b)) / Math.log(Math.abs(a)), 2, 'log', diffLog);

const diffUnaryFunctions = function (makeFirst) {
    return (arg, differential) => {
        const f = makeFirst(arg);
        const g = arg[0].diff(differential);
        return new Multiply(f, g);
    }
}

const diffLn = diffUnaryFunctions((arg) => new Divide(new Const(1), arg[0]));
const diffExp = diffUnaryFunctions((arg) => new Power(E, arg[0]));
const diffCosh = diffUnaryFunctions((arg) => new Sinh(...arg));
const diffSinh = diffUnaryFunctions((arg) => new Cosh(...arg));

const Ln = StandardFunction(a => Math.log(Math.abs(a)), 1, 'ln', diffLn);
const Exponent = StandardFunction(a => Math.pow(Math.E, a), 1, 'exp', diffExp);
const Sinh = StandardFunction(Math.sinh, 1, 'sinh', diffSinh);
const Cosh = StandardFunction(Math.cosh, 1, 'cosh', diffCosh);

const Lexemes = {
    "+": Add,
    "-": Subtract,
    "*": Multiply,
    "/": Divide,
    "negate": Negate,
    "pow": Power,
    "log": Log,
    "cosh": Cosh,
    "sinh": Sinh,
    "x": X,
    "y": Y,
    "z": Z,
};

const foldParseObject = f => regExp => expression => expression.trim().split(regExp).reduce((a, b) => f(a, b), []).pop();

const postFixParseObject = foldParseObject((stack, arg) => {
    const lexeme = Lexemes[arg] || new Const(+arg);
    stack.push(lexeme.arity === undefined ? lexeme : new lexeme(...stack.splice(-lexeme.arity)));
    return stack;
});

const parseObject = expression => postFixParseObject(/\s+/)(expression);

// let println = function () {
//     for (let value of arguments) {
//         console.log(value);
//     }
// };

