"use strict";

const standardFunction = operation => (...args) => (...vars) => operation(...(args.map(a => a(...vars))));

const add = standardFunction((a, b) => a + b);
const subtract = standardFunction((a, b) => a - b);
const multiply = standardFunction((a, b) => a * b);
const divide = standardFunction((a, b) => a / b);
const negate = standardFunction(a => -a);
const sin = standardFunction(Math.sin);
const cos = standardFunction(Math.cos);
const cube = standardFunction(a => a * a * a);
const cuberoot = standardFunction(Math.cbrt);

const avg = arity => standardFunction((...args) => args.splice(0, arity).reduce((a, b) => a + b, 0) / arity);
const med = arity => standardFunction((...args) => {
    args = args.splice(0, arity);
    args.sort((a, b) => a - b);
    return args[Math.floor(args.length / 2)];
});

const avg5 = avg(5);
const med3 = med(3);

const variable = name => (...args) => args[vars[name] || 0];
const vars = {
    "x" : 0,
    "y" : 1,
    "z" : 2
};

const cnst = val => (...args) => val;

const pi = cnst(Math.PI);
const e = cnst(Math.E);
const x = variable("x");
const y = variable("y");
const z = variable("z");

const operations = {
    "+" : [add, 2],
    "-" : [subtract, 2],
    "*" : [multiply, 2],
    "/" : [divide, 2],
    "sin" : [sin, 1],
    "cos" : [cos, 1],
    "negate" : [negate, 1],
    "cube" : [cube, 1],
    "cuberoot" : [cuberoot, 1],
    "avg5" : [avg5, 5],
    "med3" : [med3, 3]
};

const variablesAndConsts = {
    "x" : x,
    "y" : y,
    "z" : z,
    "e" : e,
    "pi" : pi
};

const foldParse = f => regExp => expression => {
    let stack = [];
    expression.trim().split(regExp).forEach(f(stack));
    return stack.pop();
};

const parseLex = lex => variablesAndConsts[lex] || cnst(+lex);

const postFixParse = foldParse(stack => lex => {
    if (lex in operations) {
        let currOp = operations[lex];
        stack.push(currOp[0](...stack.splice(stack.length - currOp[1], currOp[1])));
    } else {
        stack.push(parseLex(lex));
    }
});

const parse = expression => postFixParse(/\s+/)(expression);

// let println = function () {
//     for (let value of arguments) {
//         console.log(value);
//     }
// };
//
// println(parse("1 x +")(1));