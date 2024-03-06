package org.vdk;

import java.util.function.BiFunction;

public class Log extends SeriesFunction {
    private final Ln ln;
    private final double base;
    protected Log(int seriesLength, double base) {
        super(seriesLength);
        ln = new Ln(seriesLength);
        this.base = ln.apply(base);
    }

    @Override
    public Double apply(Double aDouble) {
        return ln.apply(aDouble) / base;
    }
}
