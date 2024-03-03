package org.vdk.task3;

import java.util.List;

public class Hero1 extends Person{
    public Hero1() {
        super("Hero1", List.of(new Wear(Color.BLUE, "Balahon"), new Wear(Color.BLACK, "Jeans")));
    }
}
