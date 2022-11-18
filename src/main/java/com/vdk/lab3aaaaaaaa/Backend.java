package com.vdk.lab3aaaaaaaa;

import lombok.Getter;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@ManagedBean(name = "backend")
@SessionScoped
public class Backend implements Serializable {
    @Getter
    List<Hit> points = new ArrayList<>();
    EntityManagerFactory factory;

    public Backend() {
        factory = Persistence.createEntityManagerFactory("base");
    }

    public List<Hit> data() {
        ArrayList<Hit> objects = new ArrayList<>(points);
        Collections.reverse(objects);
        return objects;
    }

    public List<Hit> get(){
        return new ArrayList<>(points);
    }

    public boolean add(Hit hit){
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(hit);
        transaction.commit();
        return points.add(hit);
    }
}
