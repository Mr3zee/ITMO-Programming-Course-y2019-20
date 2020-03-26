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

const diffAddSub = function(args, differential, Constructor) {
    args = args.reduce((a, b) => {a.push(b.diff(differential)); return a}, []);
    return new Constructor(...args);
}

const diffMultiply = function (args, differential) {
    const f = args.length === 2 ? args.shift() : new Multiply(...args.slice(-1)), g = args.pop();
    const fg = new Multiply(f.diff(differential), g), gf = new Multiply(g.diff(differential), f);
    return new Add(fg, gf);
}

const diffDivide = function (args, differential) {
    const f = args.length === 2 ? args.shift() : new Divide(...args.slice(-1)), g = args.pop();
    const fg = new Multiply(f.diff(differential), g), gf = new Multiply(g.diff(differential), f);
    return new Divide(new Subtract(fg, gf), new Multiply(g, g));
}

const Add = StandardFunction((a, b) => a + b, 2, '+', diffAddSub);
const Subtract = StandardFunction((a, b) => a - b, 2, '-', diffAddSub);
const Multiply = StandardFunction((a, b) => a * b, 2, '*', diffMultiply);
const Divide = StandardFunction((a, b) => a / b, 2, '/', diffDivide);
const Negate = StandardFunction(a => -a, 1, 'negate', diffAddSub);

const lexemes = {
    "+" : Add,
    "-" : Subtract,
    "*" : Multiply,
    "/" : Divide,
    "negate" : Negate,
    "x" : X,
    "y" : Y,
    "z" : Z,
};

const foldParse = f => regExp => expression => expression.trim().split(regExp).reduce((a, b) => f(a, b), []).pop();

const postFixParse = foldParse((stack, arg) => {
    const lexeme = lexemes[arg] || new Const(+arg);
    stack.push(lexeme.arity === undefined ? lexeme : new lexeme(...stack.splice(-lexeme.arity)));
    return stack;
});

const parse = expression => postFixParse(/\s+/)(expression);

let println = function () {
    for (let value of arguments) {
        console.log(value);
    }
};

let a = new Multiply(new Const(2), X);
a = a.diff("x");
println(a.evaluate(21));


