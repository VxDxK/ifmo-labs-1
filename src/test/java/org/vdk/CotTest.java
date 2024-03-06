package org.vdk;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CotTest {
    @ParameterizedTest
    @ValueSource(doubles = {-1, 0, 0.5,  2, Math.PI / 2,})
    void param(double d) {
        var cot = new Cot(10);
        assertEquals(cot.apply(d), 1.0 / Math.tan(d), 0.1);
    }
}