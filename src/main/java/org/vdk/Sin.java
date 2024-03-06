package org.vdk;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Sin extends SeriesFunction{
    private final  Factorial factorial = new Factorial();
    protected Sin(int seriesLength) {
        super(seriesLength);
    }

    @Override
    public Double apply(Double x) {
        double result = 0;

        for (long i = 0; i < seriesLength; i++) {
            double term = Math.pow(-1, i) * Math.pow(x, 2 * i + 1) / factorial.apply(2 * i + 1);
            result += term;
        }
        return result;
    }
}
