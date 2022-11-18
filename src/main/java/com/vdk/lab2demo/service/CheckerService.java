package com.vdk.lab2demo.service;


import com.vdk.lab2demo.GraphValidator;
import com.vdk.lab2demo.bean.Hits;
import com.vdk.lab2demo.bean.Shot;

import javax.ejb.EJB;

public class CheckerService {
//    @EJB
//    private Hits hits;
//    @EJB
    private GraphValidator validator;

    public CheckerService() {
    }

    public void check(Shot shot){
//        hits.getHits().add(validator.validate(shot));
    }
}
