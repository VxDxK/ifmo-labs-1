package com.vdk.lab3aaaaaaaa.mbean;

public interface StreakMBean {

    default void shot(boolean isHit) {
        if (isHit)
            hit();
        else
            miss();
    }


    void hit();

    void miss();

    long getShots();

    long getMisses();

    long getHits();

}
