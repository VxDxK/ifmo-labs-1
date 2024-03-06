package org.vdk;

public class LabSystem extends SeriesFunction {
    private final Cot cot;
    private final Sec sec;
    private final Sin sin;
    private final Ln ln;
    private final Log log3;
    private final Log log5;
    private final Log log2;


    protected LabSystem(int seriesLength) {
        super(seriesLength);
        cot = new Cot(seriesLength);
        sec = new Sec(seriesLength);
        sin = new Sin(seriesLength);
        ln = new Ln(seriesLength);
        log3 = new Log(seriesLength, 3);
        log5 = new Log(seriesLength, 5);
        log2 = new Log(seriesLength, 2);

    }

    @Override
    public Double apply(Double x) {
        if (x <= 0) {
            return ((cot.apply(x) * sec.apply(x)) - cot.apply(x)) / sin.apply(x);
        } else {
            return  Math.pow (((ln.apply(x)) * log2.apply(x))/ log5.apply(x), 3);
        }
    }
}
