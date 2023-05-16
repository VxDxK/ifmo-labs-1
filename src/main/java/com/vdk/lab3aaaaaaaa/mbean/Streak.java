package com.vdk.lab3aaaaaaaa.mbean;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;

public class Streak extends NotificationBroadcasterSupport implements StreakMBean, Serializable {

    long shots = 0;
    long misses = 0;
    boolean lastMiss = false;

    long notificationCounter = 0;

    @Override
    public void hit() {
        ++shots;
        lastMiss = false;
    }

    @Override
    public void miss() {
        ++shots;
        ++misses;
        if(lastMiss)
            sendNotification(new Notification("2 miss streak", this, notificationCounter++));
        lastMiss = true;
    }

    @Override
    public long getShots() {
        return shots;
    }

    @Override
    public long getMisses() {
        return misses;
    }

    @Override
    public long getHits() {
        return shots - misses;
    }
}
