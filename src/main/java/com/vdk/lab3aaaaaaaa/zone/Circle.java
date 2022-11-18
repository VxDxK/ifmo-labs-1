package com.vdk.lab3aaaaaaaa.zone;

import com.vdk.lab3aaaaaaaa.Pair;
import com.vdk.lab3aaaaaaaa.Point;
import lombok.AllArgsConstructor;

import static java.lang.Math.pow;

@AllArgsConstructor
public class Circle extends Area{
    private final double r;

    @Override
    public boolean zoneHit(Pair<Double, Double> xy) {
        return pow(xy.first, 2) + pow(xy.second, 2) <= pow(r, 2);
    }
}
