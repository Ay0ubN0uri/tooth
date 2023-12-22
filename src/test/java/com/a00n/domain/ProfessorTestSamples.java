package com.a00n.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProfessorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Professor getProfessorSample1() {
        return new Professor().id(1L).grade("grade1");
    }

    public static Professor getProfessorSample2() {
        return new Professor().id(2L).grade("grade2");
    }

    public static Professor getProfessorRandomSampleGenerator() {
        return new Professor().id(longCount.incrementAndGet()).grade(UUID.randomUUID().toString());
    }
}
