package org.vdk;

import java.math.BigDecimal;
import java.util.function.Function;

public abstract class SeriesFunction implements Function<Double, Double> {
    protected final int seriesLength;

    protected SeriesFunction(int seriesLength) {
        this.seriesLength = seriesLength;
    }
}
