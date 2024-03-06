package org.vdk;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {
    @ParameterizedTest
    @ValueSource(doubles = {1, 2, Math.E, 10})
    void param(double d) {
        var ln = new Log(20, 10);
        assertEquals(ln.apply(d), Math.log10(d), 0.1);
    }
}