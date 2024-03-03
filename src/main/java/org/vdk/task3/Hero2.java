package org.vdk.task3;

import java.util.List;

public class Hero2 extends Person{
    public Hero2() {
        super("Hero2", List.of(new Wear(Color.RED, "Jacket"), new Wear(Color.BLUE, "Pants")));
    }
}
