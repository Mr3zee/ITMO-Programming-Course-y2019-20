"use strict";

// let println = function () {
//     for (let value of arguments) {
//         console.log(value);
//     }
// }

let binaryOperation = operation => (first, second) => (x, y, z) => operation(first(x, y, z), second(x, y, z));
let add = binaryOperation((a, b) => a + b);
let subtract = binaryOperation((a, b) => a - b);
let multiply = binaryOperation((a, b) => a * b);
let divide = binaryOperation((a, b) => a / b);

let unaryOperation = operation => arg => (x, y, z) => operation(arg(x, y, z));
let negate = unaryOperation(a => -a);
let sin = unaryOperation(a => Math.sin(a));
let cos = unaryOperation(a => Math.cos(a));

let variable = name => (x, y, z) => name === "x" ? x : name === "y" ? y : z;
let cnst = val => (x, y, z) => val;

const pi = cnst(Math.PI);
const e = cnst(Math.E);

let binary = {
    "+" : add,
    "-": subtract,
    "*": multiply,
    "/": divide,
};

let unary = {
    "sin" : sin,
    "cos" : cos,
    "negate" : negate
}

let variablesAndConsts = {
    "x" : variable("x"),
    "y" : variable("y"),
    "z" : variable("z"),
    "e" : e,
    "pi" : pi
};

let pos;
let source;
let init = expression => {
    pos = 0;
    source = expression;
}

let hasNext = () => pos < source.length;
let isWhitespace = () => source.charAt(pos) === ' ';

let skipWhitespaces = () => {
    while (isWhitespace() && hasNext()) {
        pos++;
    }
}

let takeLex = () => {
    let ans = "";
    while(!isWhitespace() && hasNext()) {
        ans += source.charAt(pos++);
    }
    return ans;
}

let parseLex = lex => lex in variablesAndConsts ? variablesAndConsts[lex] : cnst(parseInt(lex));

let parse = expression => {
    init(expression);
    let stack = [];
    skipWhitespaces();
    while (hasNext()) {
        let lex = takeLex();
        if (lex in binary) {
            let second = stack.pop();
            stack.push(binary[lex](stack.pop(), second));
        } else if (lex in unary) {
            stack.push(unary[lex](stack.pop()));
        } else {
            stack.push(parseLex(lex));
        }
        skipWhitespaces();
    }
    return stack.pop();
}

// println(parse("x x 2 - * x * y +")(5, 4, 3));