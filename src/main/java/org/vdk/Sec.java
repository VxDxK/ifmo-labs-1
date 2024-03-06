package org.vdk;

public class Sec extends SeriesFunction{
    private final Cos cos;
    protected Sec(int seriesLength) {
        super(seriesLength);
        cos = new Cos(seriesLength);
    }

    @Override
    public Double apply(Double value) {
        return 1 / cos.apply(value);
    }
}
