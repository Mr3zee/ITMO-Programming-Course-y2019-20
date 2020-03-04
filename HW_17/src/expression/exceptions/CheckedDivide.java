//package expression.exceptions;
//
//import expression.CommonExpression;
//import expression.operations.Divide;
//import expression.exceptions.EExceptions.DivisionByZeroEException;
//import expression.exceptions.EExceptions.EvaluatingExpressionException;
//import expression.exceptions.EExceptions.OverflowEEException;
//
//public class CheckedDivide extends Divide {
//    public CheckedDivide(CommonExpression firstExp, CommonExpression secondExp) {
//        super(firstExp, secondExp);
//    }
//
//    @Override
//    protected int toCalculate(int firstArg, int secondArg) throws EvaluatingExpressionException {
//        if (secondArg == 0) {
//            throw new DivisionByZeroEException(firstArg);
//        }
//        if (firstArg == Integer.MIN_VALUE && secondArg == -1) {
//            throw new OverflowEEException("Divide", firstArg, secondArg);
//        }
//        return super.toCalculate(firstArg, secondArg);
//    }
//}
