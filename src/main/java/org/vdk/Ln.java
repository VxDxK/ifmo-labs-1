package org.vdk;

public class Ln extends SeriesFunction {
    protected Ln(int seriesLength) {
        super(seriesLength);
    }

    @Override
    public Double apply(Double value) {
        double num, mul, cal, sum = 0;

        num = (value - 1) / (value + 1);

        for (int i = 1; i <= seriesLength; i++) {
            mul = (2 * i) - 1;
            cal = Math.pow(num, mul);
            cal = cal / mul;
            sum = sum + cal;
        }

        sum = 2 * sum;
        return sum;
    }
}
