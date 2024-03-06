package org.vdk;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;

public class Factorial implements Function<Long, Long> {

    private long factorial(long value) {
        if (value <= 1)
            return 1;
        return value * factorial(value - 1);
    }

    @Override
    public Long apply(Long value) {
        return factorial(value);
    }
}
