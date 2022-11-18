package com.vdk.lab3aaaaaaaa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@ManagedBean(name = "point")
@SessionScoped
public class Point implements Serializable {
    private double x;
    private double y;
    private double r;

    @ManagedProperty(name = "backend", value = "#{backend}")
    private Backend backend;

    public void submit(){
        System.out.println("x: " + x);
        System.out.println("y: " + y);
        System.out.println("r: " + r);
        GraphValidator validator = new GraphValidator();

        TimeMeter<Boolean> timeMeter = new TimeMeter<>(() -> validator.isHit(x, y, r));
        Pair<Long, Boolean> pair = timeMeter.start();

        backend.add(new Hit(x, y, r, pair.second, LocalDateTime.now(), pair.first));
    }

}
