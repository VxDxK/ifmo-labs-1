package com.vdk.lab3aaaaaaaa;

import com.vdk.lab3aaaaaaaa.mbean.HitPercent;
import com.vdk.lab3aaaaaaaa.mbean.HitPercentMBean;
import com.vdk.lab3aaaaaaaa.mbean.Streak;
import com.vdk.lab3aaaaaaaa.mbean.StreakMBean;
import lombok.Getter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@ManagedBean(name = "backend")
@SessionScoped
public class Backend implements Serializable {
    @Getter
    List<Hit> points = new ArrayList<>();
    EntityManagerFactory factory;
    MBeanServer mBeanServer;
    HitPercentMBean hitPercent;
    StreakMBean streak;

    public Backend() {
        factory = Persistence.createEntityManagerFactory("base");
        this.mBeanServer = ManagementFactory.getPlatformMBeanServer();
        this.hitPercent = new HitPercent();
        this.streak = new Streak();
        try {
            mBeanServer.registerMBean(hitPercent, new ObjectName("VxDxK:name=percent"));
            mBeanServer.registerMBean(streak, new ObjectName("VxDxK:name=streak"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Hit> data() {
        ArrayList<Hit> objects = new ArrayList<>(points);
        Collections.reverse(objects);
        return objects;
    }

    public List<Hit> get() {
        return new ArrayList<>(points);
    }

    public boolean add(Hit hit) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        hitPercent.shot(hit.isHit());
        streak.shot(hit.isHit());
//        transaction.begin();
//        manager.persist(hit);
//        transaction.commit();
        return points.add(hit);
    }
}
