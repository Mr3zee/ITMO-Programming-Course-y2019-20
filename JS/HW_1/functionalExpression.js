"use strict";


const binaryOperation = operation => (first, second) => (x, y, z) => operation(first(x, y, z), second(x, y, z));
const add = binaryOperation((a, b) => a + b);
const subtract = binaryOperation((a, b) => a - b);
const multiply = binaryOperation((a, b) => a * b);
const divide = binaryOperation((a, b) => a / b);

const unaryOperation = operation => arg => (x, y, z) => operation(arg(x, y, z));
const negate = unaryOperation(a => -a);
const sin = unaryOperation(a => Math.sin(a));
const cos = unaryOperation(a => Math.cos(a));
const cube = unaryOperation(a => a * a * a);
const cuberoot = unaryOperation(a => Math.cbrt(a));

const averageOperation = operation => arity => (...args) => (x, y, z) => {
    let values = typeof (args[0]) === "object" ? args[0] : args;
    return operation(arity)(values)(x, y, z);
};

const avg = averageOperation(arity => (args) => (x, y, z) => {
    let ans = 0;
    for (let i = 0; i < arity; i++) {
        ans += args[i](x, y, z);
    }
    return ans / arity;
});
const avg5 = avg(5);
const med = averageOperation(arity => args => (x, y, z) => {
    let a = [];
    for (let i = 0; i < arity; i++) {
        a[i] = args[i](x, y, z);
    }
    a.sort((a, b) => a - b);
    return a[Math.floor(a.length / 2)];
});
const med3 = med(3);

const variable = name => (x, y, z) => name === "x" ? x : name === "y" ? y : z;
const cnst = val => (x, y, z) => val;

const pi = cnst(Math.PI);
const e = cnst(Math.E);
const x = variable("x");
const y = variable("y");
const z = variable("z");

const binary = {
    "+" : add,
    "-" : subtract,
    "*" : multiply,
    "/" : divide,
};

const unary = {
    "sin" : sin,
    "cos" : cos,
    "negate" : negate,
    "cube" : cube,
    "cuberoot" : cuberoot
}

const variablesAndConsts = {
    "x" : x,
    "y" : y,
    "z" : z,
    "e" : e,
    "pi" : pi
};

const average = {
    "avg5" : [avg5, 5],
    "med3" : [med3, 3]
};

const parseLex = lex => lex in variablesAndConsts ? variablesAndConsts[lex] : cnst(parseInt(lex));

const parse = expression => {
    let stack = [];
    expression.trim().split(/\s+/).forEach(lex => {
        if (lex in binary) {
            let second = stack.pop();
            stack.push(binary[lex](stack.pop(), second));
        } else if (lex in unary) {
            stack.push(unary[lex](stack.pop()));
        } else if (lex in average) {
            let operation = average[lex];
            stack.push(operation[0](stack.splice(stack.length - operation[1], operation[1])));
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

