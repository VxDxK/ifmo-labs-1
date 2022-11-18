package com.vdk.lab2demo.bean;

import javax.ejb.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@LocalBean
@Stateful
public class Hits implements Serializable {
    private final List<HitResult> hits;

    public Hits() {
        this(new ArrayList<>());
    }

    public Hits(List<HitResult> hits) {
        this.hits = hits;
    }

    public List<HitResult> getHits() {
        return hits;
    }
}
