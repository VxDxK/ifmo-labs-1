package org.vdk.task3;

import java.util.ArrayList;
import java.util.List;

public class RoomEnterAction implements Action{
    public final Room room;
    public List<Person> persons = new ArrayList<>();

    public RoomEnterAction(Room room, List<Person> persons) {
        this.room = room;
        this.persons.addAll(persons);
    }

    @Override
    public void play() {
        room.persons.addAll(persons);
    }
}
