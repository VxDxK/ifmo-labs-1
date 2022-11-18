package com.vdk.lab3aaaaaaaa.zone;

import com.vdk.lab3aaaaaaaa.Pair;

import java.util.function.Predicate;

public enum Quarter {
    FIRST((coors) -> coors.first >= 0 && coors.second >= 0),
    SECOND((coors) -> coors.first >= 0 && coors.second >= 0),
    THIRD((coors) -> coors.first >= 0 && coors.second >= 0),
    FOURTH((coors) -> coors.first >= 0 && coors.second >= 0);
    public final Predicate<Pair<Double, Double>> checkFunc;

    Quarter(Predicate<Pair<Double, Double>> checkFunc) {
        this.checkFunc = checkFunc;
    }
}
