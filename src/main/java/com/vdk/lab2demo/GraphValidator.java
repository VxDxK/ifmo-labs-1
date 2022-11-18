package com.vdk.lab2demo;

import com.vdk.lab2demo.bean.HitResult;
import com.vdk.lab2demo.bean.Shot;
import io.prometheus.client.Counter;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import static java.lang.Math.*;
import static java.lang.Math.abs;

@Singleton
public class GraphValidator {
    private Counter shotCounter;
    private Counter hitCounter;

    @PostConstruct
    private void init(){
        shotCounter = Counter.build("shot_counter", "Counts all shots").register();
        hitCounter = Counter.build("user_hit_counts", "How much shots hitted plain").register();
    }


    public boolean validate(double x, double y){
        if(y >= 0){
            double yGr = functionGreen(x);
            double yOr = functionOrange(x);
            if(!Double.isNaN(yGr)){
                return y <= yGr;
            }else{
                return y <= yOr;
            }
        }else {
            double yBl = functionBlue(x);
            double yRd = functionRed(x);
            if(!Double.isNaN(yBl)){
                return y >= yBl;
            }else{
                return y >= yRd;
            }
        }
    }

    public synchronized boolean isHit(Monadka<Shot> shot){
        shotCounter.inc();
        double x = shot.map(sh -> (double)sh.getX()/sh.getR()).get();
        double y = shot.map(sh -> sh.getY()/sh.getR()).get();

        boolean res = validate(x, y);
        if(res){
            hitCounter.inc();
        }
        return res;
    }


    private double functionGreen(double x){
        return 2*sqrt(-abs(abs(x)-1)*abs(3-abs(x))/((abs(x)-1)*(3-abs(x))))*(1+abs(abs(x)-3)/(abs(x)-3))*sqrt(1- pow((x/7), 2))+(5+0.97*(abs(x-.5)+abs(x+.5))-3*(abs(x-.75)+abs(x+.75)))*(1+abs(1-abs(x))/(1-abs(x)));
    }

    private double functionBlue(double x){
        return -3*sqrt(1-pow((x/7), 2))*sqrt(abs(abs(x)-4)/(abs(x)-4));
    }

    private double functionRed(double x){
        return abs(x/2)-0.0913722*(pow(x, 2))-3+sqrt(1-pow((abs(abs(x)-2)-1), 2));
    }

    private double functionOrange(double x){
        return (2.71052+(1.5-0.5*abs(x))-1.35526*sqrt(4-pow((abs(x)-1), 2)))*sqrt(abs(abs(x)-1)/(abs(x)-1))+0.9;
    }



}
