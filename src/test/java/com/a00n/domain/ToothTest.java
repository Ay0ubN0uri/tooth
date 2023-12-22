package com.a00n.domain;

import static com.a00n.domain.PWTestSamples.*;
import static com.a00n.domain.ToothTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.a00n.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ToothTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tooth.class);
        Tooth tooth1 = getToothSample1();
        Tooth tooth2 = new Tooth();
        assertThat(tooth1).isNotEqualTo(tooth2);

        tooth2.setId(tooth1.getId());
        assertThat(tooth1).isEqualTo(tooth2);

        tooth2 = getToothSample2();
        assertThat(tooth1).isNotEqualTo(tooth2);
    }

    @Test
    void pwTest() throws Exception {
        Tooth tooth = getToothRandomSampleGenerator();
        PW pWBack = getPWRandomSampleGenerator();

        tooth.addPw(pWBack);
        assertThat(tooth.getPws()).containsOnly(pWBack);
        assertThat(pWBack.getTooth()).isEqualTo(tooth);

        tooth.removePw(pWBack);
        assertThat(tooth.getPws()).doesNotContain(pWBack);
        assertThat(pWBack.getTooth()).isNull();

        tooth.pws(new HashSet<>(Set.of(pWBack)));
        assertThat(tooth.getPws()).containsOnly(pWBack);
        assertThat(pWBack.getTooth()).isEqualTo(tooth);

        tooth.setPws(new HashSet<>());
        assertThat(tooth.getPws()).doesNotContain(pWBack);
        assertThat(pWBack.getTooth()).isNull();
    }
}
