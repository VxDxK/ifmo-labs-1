package org.vdk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LabSystemTest {

    @Test
    void simpleTest() {
        var labSys = new LabSystem(20);
//        assertEquals(labSys.apply(-2 * Math.PI), 0.5, 0.1);
        System.out.println(labSys.apply(-0.46));
    }
}