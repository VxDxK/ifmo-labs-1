package org.vdk;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.function.Function;

public class CsvGraphWriter {
    private final BufferedWriter writer;
    private final Function<Double, Double> function;

    public CsvGraphWriter(BufferedWriter writer, Function<Double, Double> function) {
        this.writer = writer;
        this.function = function;
    }

    public void write(double begin, double end, double delta) throws IOException {
        writer.write("x,y\n");
        for (double i = begin; i <= end; i+= delta) {
            writer.write(String.format("%f,%f\n", i, function.apply(i)));
        }
        writer.flush();
    }
}
