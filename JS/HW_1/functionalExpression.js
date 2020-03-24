"use strict";

const standardFunction = (operation, arity) => {
    const func = (...args) => (...vars) => operation(...(args.map(a => a(...vars))));
    func.arity = arity;
    return func;
}

const add = standardFunction((a, b) => a + b, 2);
const subtract = standardFunction((a, b) => a - b, 2);
const multiply = standardFunction((a, b) => a * b, 2);
const divide = standardFunction((a, b) => a / b, 2);
const negate = standardFunction(a => -a, 1);
const sin = standardFunction(Math.sin, 1);
const cos = standardFunction(Math.cos, 1);
const cube = standardFunction(a => a * a * a, 1);
const cuberoot = standardFunction(Math.cbrt, 1);

const avg = arity => standardFunction((...args) => args.splice(0, arity).reduce((a, b) => a + b, 0) / arity, arity);
const med = arity => standardFunction((...args) => {
    args = args.splice(0, arity);
    args.sort((a, b) => a - b);
    return args[Math.floor(args.length / 2)];
}, arity);

const avg5 = avg(5);
const med3 = med(3);

const variable = name => (...args) => args[vars[name] || 0];
const vars = {
    "x" : 0,
    "y" : 1,
    "z" : 2
};

const cnst = val => () => val;

const pi = cnst(Math.PI);
const e = cnst(Math.E);
const x = variable("x");
const y = variable("y");
const z = variable("z");

const lexemes = {
    "+" : add,
    "-" : subtract,
    "*" : multiply,
    "/" : divide,
    "sin" : sin,
    "cos" : cos,
    "negate" : negate,
    "cube" : cube,
    "cuberoot" : cuberoot,
    "avg5" : avg5,
    "med3" : med3,
    "x" : x,
    "y" : y,
    "z" : z,
    "e" : e,
    "pi" : pi
};

const foldParse = f => regExp => expression => expression.trim().split(regExp).reduce((a, b) => f(a, b), []).pop();

const postFixParse = foldParse((stack, arg) => {
    const lexeme = lexemes[arg] || cnst(+arg);
    stack.push(lexeme.arity === undefined ? lexeme : lexeme(...stack.splice(-lexeme.arity)));
    return stack;
});

const parse = expression => postFixParse(/\s+/)(expression);

// let println = function () {
//     for (let value of arguments) {
//         console.log(value);
//     }
// };
//
// println(parse("1 x +")(1));
