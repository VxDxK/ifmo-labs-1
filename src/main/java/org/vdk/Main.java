package org.vdk;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Math.*;

public class Main {

    public static void main(String[] args) throws IOException {
        var func = new Log(10, 2);
        CsvGraphWriter writer = null;

        writer = new CsvGraphWriter(new BufferedWriter(new FileWriter("fac.csv")), (x) -> {
            var fac = new Factorial();
            return fac.apply(x.longValue()).doubleValue();
        });
        writer.write(0, 6, 1);

        writer = new CsvGraphWriter(new BufferedWriter(new FileWriter("sin.csv")), new Sin(10));
        writer.write(-2 * PI, 2 * PI, 0.1);

        writer = new CsvGraphWriter(new BufferedWriter(new FileWriter("cos.csv")), new Cos(10));
        writer.write(-2 * PI, 2 * PI, 0.1);

        writer = new CsvGraphWriter(new BufferedWriter(new FileWriter("sec.csv")), new Sec(10));
//        writer = new CsvGraphWriter(new BufferedWriter(new FileWriter("sec.csv")), (x) -> 1.0 / cos(x));
        writer.write(-2 * PI, 2 * PI, 0.1);

        writer = new CsvGraphWriter(new BufferedWriter(new FileWriter("cot.csv")), new Cot(10));
        writer.write(-3 * PI, 3 * PI, 0.1);

        writer = new CsvGraphWriter(new BufferedWriter(new FileWriter("ln.csv")), new Ln(10));
        writer.write(0.1, 11, 0.1);

        writer = new CsvGraphWriter(new BufferedWriter(new FileWriter("log2.csv")), new Log(10, 2));
        writer.write(0.1, 11, 0.1);


        writer = new CsvGraphWriter(new BufferedWriter(new FileWriter("sys.csv")), new LabSystem(10));
        writer.write(-10, 10, 0.01);

        //(((cot(x) * sec(x)) - cot(x)) / sin(x))
        writer = new CsvGraphWriter(new BufferedWriter(new FileWriter("neg.csv")), (x) -> {
            return ((((1.0 / tan(x)) * (1.0 / cos(x))) - (1.0 / tan(x))) / sin(x));
        });
        writer.write(-10, 0, 0.01);
    }
}