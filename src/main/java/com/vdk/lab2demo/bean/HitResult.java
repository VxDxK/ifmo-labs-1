package com.vdk.lab2demo.bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class HitResult {
    private final int x;
    private final double y;
    private final int r;
    private final boolean hit;
    private final LocalDateTime time;
    private final double processingTime;

    public HitResult(Shot shot, boolean hit, double processingTime){
        this(shot.getX(), shot.getY(), shot.getR(), hit, processingTime);
    }

    public HitResult(int x, double y, int r, boolean hit, double processingTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.processingTime = processingTime;
        this.time = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HitResult)) return false;
        HitResult hitResult = (HitResult) o;
        return x == hitResult.x && Double.compare(hitResult.y, y) == 0 && r == hitResult.r && hit == hitResult.hit && Double.compare(hitResult.processingTime, processingTime) == 0;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r, hit, processingTime);
    }

    @Override
    public String toString() {
        return "HitResult{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", hit=" + hit +
                ", time=" + time +
                ", processingTime=" + processingTime +
                '}';
    }

    public int getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public boolean isHit() {
        return hit;
    }

    public double getProcessingTime() {
        return processingTime;
    }
}
