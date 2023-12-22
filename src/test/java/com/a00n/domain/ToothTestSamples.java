package com.a00n.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ToothTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Tooth getToothSample1() {
        return new Tooth().id(1L).name("name1");
    }

    public static Tooth getToothSample2() {
        return new Tooth().id(2L).name("name2");
    }

    public static Tooth getToothRandomSampleGenerator() {
        return new Tooth().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
