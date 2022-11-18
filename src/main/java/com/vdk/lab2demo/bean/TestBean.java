package com.vdk.lab2demo.bean;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless
public interface TestBean {
    String info();
}
