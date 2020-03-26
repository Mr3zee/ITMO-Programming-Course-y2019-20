"use strict";

function StandardFunction(operation, arity, operand) {
    const f = function (...args) {
        this.toString = function () {
            return args.reduce((a, b) => a + " " + b) + " " + operand;
        }
        this.evaluate = function (...vars) {
            return operation(...args.map(a => a.evaluate(...vars)));
        }
        this.diff = function (name) {
            args = args.reduce((a, b) => {
                a.push(b.diff(name));
                return a;
            }, []);
            return this;
        }
    }
    f.arity = arity;
    return f;
}

const Add = StandardFunction((a, b) => a + b, 2, '+');
const Subtract = StandardFunction((a, b) => a - b, 2, '-');
const Multiply = StandardFunction((a, b) => a * b, 2, '*');
const Divide = StandardFunction((a, b) => a / b, 2, '/');
const Negate = StandardFunction(a => -a, 1, 'negate');

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
    this.diff = function (name) {
        return new Const(1);
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

let expr = new Subtract(
    new Multiply(
        new Const(2),
        new Variable("x")
    ),
    new Const(3)
);

println(expr.diff("x").toString());


