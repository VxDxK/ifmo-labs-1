package org.vdk.task3;

import java.util.ArrayList;
import java.util.List;

public class Room {
    public String name;
    public List<Person> persons = new ArrayList<>();

    public Room(String name) {
        this(name, List.of());
    }

    public Room(String name, List<Person> persons) {
        this.name = name;
        this.persons.addAll(persons);
    }
}
