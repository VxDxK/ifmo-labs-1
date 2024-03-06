package org.vdk;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CosTest {
    @ParameterizedTest
    @ValueSource(doubles = {-Math.PI, -1, 0, 0.5,  2, Math.PI, Math.PI * 2})
    void param(double d) {
        var cos = new Cos(10);
        assertEquals(cos.apply(d), Math.cos(d), 0.1);
    }
}