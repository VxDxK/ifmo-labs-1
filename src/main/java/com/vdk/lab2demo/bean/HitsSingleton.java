package com.vdk.lab2demo.bean;

import javax.ejb.EJB;
import javax.ejb.Singleton;

@Singleton
public class HitsSingleton {
    @EJB
    Hits hits;

    public HitsSingleton() {

    }

    public Hits getHits() {
        return hits;
    }
}
