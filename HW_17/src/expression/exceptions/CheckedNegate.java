//package expression.exceptions;
//
//import expression.CommonExpression;
//import expression.operations.Negate;
//import expression.exceptions.EExceptions.EvaluatingExpressionException;
//import expression.exceptions.EExceptions.OverflowEEException;
//
//public class CheckedNegate extends Negate {
//    public CheckedNegate(CommonExpression expression) {
//        super(expression);
//    }
//
//    @Override
//    protected int toCalculate(int arg) throws EvaluatingExpressionException {
//        if (arg == Integer.MIN_VALUE) {
//            throw new OverflowEEException("Negate", arg);
//        }
//        return super.toCalculate(arg);
//    }
//}
