package com.a00n.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PWTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PW getPWSample1() {
        return new PW().id(1L).title("title1").objectif("objectif1").docs("docs1");
    }

    public static PW getPWSample2() {
        return new PW().id(2L).title("title2").objectif("objectif2").docs("docs2");
    }

    public static PW getPWRandomSampleGenerator() {
        return new PW()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .objectif(UUID.randomUUID().toString())
            .docs(UUID.randomUUID().toString());
    }
}
