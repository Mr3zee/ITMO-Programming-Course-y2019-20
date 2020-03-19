"use strict";

let println = function () {
    for (let value of arguments) {
        console.log(value);
    }
}

let binaryOperation = operation => (first, second) => x => operation(first(x), second(x));
let add = binaryOperation((a, b) => a + b);
let subtract = binaryOperation((a, b) => a - b);
let multiply = binaryOperation((a, b) => a * b);
let divide = binaryOperation((a, b) => a / b);

let unaryOperation = operation => arg => x => operation(arg(x));
let negate = unaryOperation(a => -a);
let variable = name => x => x;
let cnst = val => x => val;

let expr = add(
    subtract(
        multiply(
            variable("x"),
            variable("x")
        ),
        multiply(
            cnst(2),
            variable("x")
        )
    ),
    cnst(1)
);

// for (let x = 0; x <= 10; x++) {
//     println(expr(x));
// }

let operationMap = {
    "+" : add,
    "-": subtract,
    "*": multiply,
    "/": divide,
};

let variables = {
    "x" : variable("x")
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

let parseLex = lex => lex in variables ? variables[lex] : cnst(parseInt(lex));

let parse = expression => {
    init(expression);
    let stack = [];
    skipWhitespaces();
    while (hasNext()) {
        let lex = takeLex();
        if (lex in operationMap) {
            let second = stack.pop();
            stack.push(operationMap[lex](stack.pop(), second));
        } else {
            stack.push(parseLex(lex));
        }
        skipWhitespaces();
    }
    return stack.pop();
}

println(parse("x x 2 - * x * 1 +")(5));