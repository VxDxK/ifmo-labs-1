package com.vdk.lab2demo.bean;

import javax.ejb.Stateless;

public class TestBeanImpl implements TestBean {
    @Override
    public String info() {
        return "Bebrachka";
    }
}
