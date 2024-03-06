package org.vdk;

public class Cos extends SeriesFunction{
    private final Sin sin;
    protected Cos(int seriesLength) {
        super(seriesLength);
        sin = new Sin(seriesLength);
    }

    @Override
    public Double apply(Double value) {
        //cos(a) = sin(pi/2 - a)

        return sin.apply(Math.PI / 2 - value);
    }
}
