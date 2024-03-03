package org.vdk.task3;

import java.util.ArrayList;
import java.util.List;

public class Person {
    public String name;
    public final List<Wear> wears = new ArrayList<>();

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, List<Wear> wears) {
        this.name = name;
        this.wears.addAll(wears);
    }
}
