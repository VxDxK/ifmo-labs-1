package org.vdk;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LnTest {
    @ParameterizedTest
    @ValueSource(doubles = {0.5, 1, 2, Math.E, 10})
    void param(double d) {
        var ln = new Ln(20);
        assertEquals(ln.apply(d), Math.log(d), 0.1);
    }
}