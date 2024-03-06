package org.vdk;

public class Cot extends SeriesFunction{
    private final Sin sin;
    private final Cos cos;


    protected Cot(int seriesLength) {
        super(seriesLength);
        sin = new Sin(seriesLength);
        cos = new Cos(seriesLength);
    }

    @Override
    public Double apply(Double value) {
        return cos.apply(value) / sin.apply(value);
    }
}
