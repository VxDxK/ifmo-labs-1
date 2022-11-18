package com.vdk.lab3aaaaaaaa.zone;

import com.vdk.lab3aaaaaaaa.Pair;
import com.vdk.lab3aaaaaaaa.Point;

import java.util.ArrayList;
import java.util.List;

public abstract class Area {
    private final List<Quarter> quarterList = new ArrayList<>();

    public boolean checkHit(Pair<Double, Double> xy){
        for (Quarter q : quarterList) {
            if(q.checkFunc.test(xy)){
                return zoneHit(xy);
            }
        }
        return false;
    }

    public abstract boolean zoneHit(Pair<Double, Double> xy);

}
