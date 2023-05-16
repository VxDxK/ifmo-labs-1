package com.vdk.lab3aaaaaaaa.mbean;

import java.io.Serializable;

public class HitPercent implements HitPercentMBean, Serializable {

    long shots = 0;

    long hits = 0;

    @Override
    public double getPercentOfHits() {
        if(shots == 0)
            return 0;
        return (double) hits / shots * 100d;
    }

    @Override
    public void hit() {
        ++shots;
        ++hits;
    }

    @Override
    public void miss() {
        ++shots;
    }
}
