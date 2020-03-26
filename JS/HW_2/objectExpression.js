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
        return args[vars[name] || 0];
    }
    this.diff = function (differential) {
        return differential === name ? new Const(1) : new Const(0);
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

const diffMultiply = diffMultiplyDivide((fg, gf) => new Add(fg, gf));
const diffDivide = diffMultiplyDivide((fg, gf, f, g) => new Divide(new Subtract(fg, gf), new Multiply(g, g)));

const Add = StandardFunction((a, b) => a + b, 2, '+', diffAddSub);
const Subtract = StandardFunction((a, b) => a - b, 2, '-', diffAddSub);
const Multiply = StandardFunction((a, b) => a * b, 2, '*', diffMultiply);
const Divide = StandardFunction((a, b) => a / b, 2, '/', diffDivide);
const Negate = StandardFunction(a => -a, 1, 'negate', diffAddSub);
const Power = StandardFunction(Math.pow, 2, 'pow', diffPower);
const Log = StandardFunction((a, b) => Math.log(Math.abs(b)) / Math.log(Math.abs(a)), 2, 'log', diffLog);
const Sinh = StandardFunction(Math.sinh, 1, 'sinh');
const Cosh = StandardFunction(Math.cosh, 1, 'cosh');

const diffEFunctions = (First, cnst) => {
    return (arg, differential) => {
        const f = new First(cnst, arg[0]);
        const g = arg[0].diff(differential);
        return new Multiply(f, g);
    }
}

const diffLn = diffEFunctions(Divide.constructor, new Const(1));
const diffExp = diffEFunctions(Power.constructor, e);

const Ln = StandardFunction(a => Math.log(Math.abs(a)), 1, 'ln', diffLn);
const Exponent = StandardFunction(a => Math.pow(Math.E, a), 1, 'exp', diffExp);

const MultiFunction = (f, zero, diff) => (arity, string) =>
    StandardFunction((...args) => args.splice(0, arity).reduce((a, b) => f(a, b), zero), arity, string, diff);
const Min = MultiFunction(Math.min, Infinity, diffAddSub);
const Max = MultiFunction(Math.max, -Infinity, diffAddSub);
const Min3 = Min(3, "min3");
const Max5 = Max(5, "max5");

const lexemes = {
    "+": Add,
    "-": Subtract,
    "*": Multiply,
    "/": Divide,
    "negate": Negate,
    "min3": Min3,
    "max5": Max5,
    "pow": Power,
    "log": Log,
    "cosh": Cosh,
    "sinh": Sinh,
    "x": X,
    "y": Y,
    "z": Z,
};

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
// let b = new Subtract(new Variable('x'), new Variable('z'));
// let c = new Multiply(new Const(4), b);
// let f = new Add(new Const(2), c);
// let c1 = new Multiply(new Const(2), b);
// let f1 = new Add(new Const(1), c1);
// let a = new Log(f, f1)
// a = a.diff("x");
// let h = a.evaluate(2, 3, 3);
// println(h);

