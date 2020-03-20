"use strict";

const standardFunction = arity => operation => (...args) => (...vars) => operation(...(args.map(a => a(...vars)).splice(0, arity)));

const add = standardFunction(2)((a, b) => a + b);
const subtract = standardFunction(2)((a, b) => a - b);
const multiply = standardFunction(2)((a, b) => a * b);
const divide = standardFunction(2)((a, b) => a / b);
const negate = standardFunction(2)(a => -a);
const sin = standardFunction(1)(Math.sin);
const cos = standardFunction(1)(Math.cos);
const cube = standardFunction(1)(a => a * a * a);
const cuberoot = standardFunction(1)(Math.cbrt);

const avg = arity => standardFunction(arity)((...args) => args.reduce((a, b) => a + b, 0) / args.length);
const med = arity => standardFunction(arity)((...args) => {
    args.sort((a, b) => a - b);
    return args[Math.floor(args.length / 2)];
});

const avg5 = avg(5);
const med3 = med(3);

const variable = name => (...args) => name === "x" ? args[0] : name === "y" ? args[1] : args[2];
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

const parseLex = lex => lex in variablesAndConsts ? variablesAndConsts[lex] : cnst(parseInt(lex));

const parse = expression => {
    let stack = [];
    expression.trim().split(/\s+/).forEach(lex => {
        if  (lex in operations) {
            let currOp = operations[lex];
            stack.push(currOp[0](...stack.splice(stack.length - currOp[1], currOp[1])));
        } else {
            stack.push(parseLex(lex));
        }
    });
    return stack.pop();
};

// let println = function () {
//     for (let value of arguments) {
//         console.log(value);
//     }
// };

