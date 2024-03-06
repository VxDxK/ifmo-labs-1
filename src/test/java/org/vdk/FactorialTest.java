package org.vdk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {

    @Test
    void param() {
        var factorial = new Factorial();

        assertEquals(factorial.apply(5L), 120);
        assertEquals(factorial.apply(0L), 1);
        assertEquals(factorial.apply(1L), 1);

    }
}