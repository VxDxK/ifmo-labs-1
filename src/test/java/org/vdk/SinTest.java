package org.vdk;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SinTest {
    @ParameterizedTest
    @ValueSource(doubles = {-1, 0, 0.5,  2, Math.PI, Math.PI * 2})
    void param(double d) {
        var sin = new Sin(10);
        assertEquals(sin.apply(d), Math.sin(d), 0.1);
    }
}