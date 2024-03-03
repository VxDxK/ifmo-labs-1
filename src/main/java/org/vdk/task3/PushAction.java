package org.vdk.task3;

import java.util.List;

public class PushAction implements Action{
    public final Room room;
    public final List<Person> whom;

    public PushAction(Room room, List<Person> whom) {
        this.room = room;
        this.whom = whom;
    }

    @Override
    public void play() {
        room.persons.removeAll(whom);
    }
}
