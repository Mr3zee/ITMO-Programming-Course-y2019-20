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

let operationMap = {"+" : add, "-": subtract, "*": multiply, "/": divide};
