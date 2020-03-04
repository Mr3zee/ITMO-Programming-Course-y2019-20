package expression.generic;

import expression.exceptions.EExceptions.*;
import expression.type.*;

import java.math.BigInteger;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ExpressionException {
        switch (mode) {
            case "i" :
                return new TableGenerator<Integer>().tabulate(new IntegerEType(null), expression, x1, x2, y1, y2, z1, z2);
            case "d" :
                return new TableGenerator<Double>().tabulate(new DoubleEType(null), expression, x1, x2, y1, y2, z1, z2);
            case "bi" :
                return new TableGenerator<BigInteger>().tabulate(new BigIntegerEType(null), expression, x1, x2, y1, y2, z1, z2);
            default:
                throw new UnsupportedETypeException(mode);
        }
    }
}
