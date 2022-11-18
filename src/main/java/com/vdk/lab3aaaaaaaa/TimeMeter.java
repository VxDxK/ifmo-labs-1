package com.vdk.lab3aaaaaaaa;

import java.util.concurrent.Callable;

public class TimeMeter<T> {
    private final Callable<T> task;

    public TimeMeter(Callable<T> task) {
        this.task = task;
    }

    public Pair<Long, T> start() {
        long startTime = System.nanoTime();
        T t = null;
        try {
            t = task.call();
        }catch (Exception e){}
        return new Pair<>(System.nanoTime() - startTime, t);
    }

}
