package org.vdk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.vdk.Task1.expandSin;

class Task1Test {

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0, 0.5,  2, Math.PI, Math.PI * 2})
    void param(double d) {
        assertEquals(expandSin(d, 10), Math.sin(d), 0.1);
    }

    @Test
    void sinusTesting() {
        assertEquals(expandSin(Math.toRadians(0), 10), 0, 0.1);
        assertEquals(expandSin(Math.toRadians(90), 10), 1, 0.1);
        assertEquals(expandSin(Math.toRadians(180), 10), 0, 0.1);
        assertEquals(expandSin(Math.toRadians(270), 10), -1, 0.1);
        assertEquals(expandSin(Math.toRadians(360), 10), 0, 0.1);
        assertEquals(expandSin(0.5, 10), Math.sin(0.5), 0.1);
    }
}