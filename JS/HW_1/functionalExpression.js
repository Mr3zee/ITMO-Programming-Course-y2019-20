"use strict";

// let println = function () {
//     for (let value of arguments) {
//         console.log(value);
//     }
// }

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

const variable = name => (x, y, z) => name === "x" ? x : name === "y" ? y : z;
const cnst = val => (x, y, z) => val;

const pi = cnst(Math.PI);
const e = cnst(Math.E);
const x = variable("x");
const y = variable("y");
const z = variable("z");

const binary = {
    "+" : add,
    "-": subtract,
    "*": multiply,
    "/": divide,
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

const parseLex = lex => lex in variablesAndConsts ? variablesAndConsts[lex] : cnst(parseInt(lex));

const parse = expression => {
    let stack = [];
    expression.trim().split(/\s+/).forEach(lex => {
        // let lex = takeLex();
        if (lex in binary) {
            let second = stack.pop();
            stack.push(binary[lex](stack.pop(), second));
        } else if (lex in unary) {
            stack.push(unary[lex](stack.pop()));
        } else {
            stack.push(parseLex(lex));
        }
    });
    return stack.pop();
}

// println(parse(' 10         ')(0, 0, 0));