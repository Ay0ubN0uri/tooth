package com.a00n.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AcademicYearTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AcademicYear getAcademicYearSample1() {
        return new AcademicYear().id(1L).year("year1");
    }

    public static AcademicYear getAcademicYearSample2() {
        return new AcademicYear().id(2L).year("year2");
    }

    public static AcademicYear getAcademicYearRandomSampleGenerator() {
        return new AcademicYear().id(longCount.incrementAndGet()).year(UUID.randomUUID().toString());
    }
}
