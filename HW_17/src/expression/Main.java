package expression;

import expression.generic.GenericTabulator;
import expression.parser.ExpressionParser;
import expression.type.ShortEType;

public class Main {
    public static void main(String[] args) {
        String expression = "+x  y";
        Object[][][] objects = new GenericTabulator().tabulate("f", expression, 0, 0, 0,0, 0, 0);
    }
}