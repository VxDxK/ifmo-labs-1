package com.vdk.lab3aaaaaaaa;


public class GraphValidator {

    public boolean isHit(double x, double y, double r){
        if (x < 0 && y > 0)
            return false;

        if (x >= 0 && y >= 0)
            return -y >= x - r/2;

        if (x >= 0 && y < 0)
            return x*x + y*y <= r*r;


        if(x < 0 && y < 0)
            return x >= -r/2 && y >= -r;

        return false;
    }
}
