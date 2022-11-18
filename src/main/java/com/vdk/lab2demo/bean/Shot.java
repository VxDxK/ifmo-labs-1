package com.vdk.lab2demo.bean;

import com.vdk.lab2demo.ValidationException;

import java.util.Objects;

public class Shot {
    private int x;
    private double y;
    private int r;

    public final static Shot EMPTY = new Shot(0, 0, 0);

    public Shot(int x, double y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
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

    public Shot setX(int x) {
        this.x = x;
        return this;
    }

    public Shot setY(double y) {
        this.y = y;
        return this;
    }

    public Shot setR(int r) {
        this.r = r;
        return this;
    }

    @Override
    public String toString() {
        return "Shot{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shot)) return false;
        Shot shot = (Shot) o;
        return x == shot.x && Double.compare(shot.y, y) == 0 && r == shot.r;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r);
    }

    public static class ShotBuilder{
        private int x;
        private double y;
        private int r;

        public ShotBuilder(int x, double y, int r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        public boolean validateX(){
            return -5 <= x && x <= 3;
        }

        public boolean validateY(){
            return -3 <= y && y <= 5;
        }

        public boolean validateR(){
            return 1 <= r && r <= 5;
        }

        public int getX() {
            return x;
        }

        public ShotBuilder setX(int x) {
            this.x = x;
            return this;
        }

        public double getY() {
            return y;
        }

        public ShotBuilder setY(double y) {
            this.y = y;
            return this;
        }

        public int getR() {
            return r;
        }

        public ShotBuilder setR(int r) {
            this.r = r;
            return this;
        }

        public Shot build() throws ValidationException{
            if(!(validateX() && validateY() && validateR())){
                throw new ValidationException("Invalid fields value");
            }
            return new Shot(x, y, r);
        }

    }

}
