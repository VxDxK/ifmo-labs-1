package org.vdk;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SecTest {
    @ParameterizedTest
    @ValueSource(doubles = {-Math.PI, -1, 0, 0.5,  2, Math.PI, Math.PI * 2})
    void param(double d) {
        var sec = new Sec(10);
        assertEquals(sec.apply(d), 1.0 / Math.cos(d), 0.1);
    }
}