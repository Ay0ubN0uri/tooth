package com.a00n.domain;

import static com.a00n.domain.AcademicYearTestSamples.*;
import static com.a00n.domain.PWTestSamples.*;
import static com.a00n.domain.StudentPWTestSamples.*;
import static com.a00n.domain.StudentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.a00n.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StudentPWTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentPW.class);
        StudentPW studentPW1 = getStudentPWSample1();
        StudentPW studentPW2 = new StudentPW();
        assertThat(studentPW1).isNotEqualTo(studentPW2);

        studentPW2.setId(studentPW1.getId());
        assertThat(studentPW1).isEqualTo(studentPW2);

        studentPW2 = getStudentPWSample2();
        assertThat(studentPW1).isNotEqualTo(studentPW2);
    }

    @Test
    void studentTest() throws Exception {
        StudentPW studentPW = getStudentPWRandomSampleGenerator();
        Student studentBack = getStudentRandomSampleGenerator();

        studentPW.setStudent(studentBack);
        assertThat(studentPW.getStudent()).isEqualTo(studentBack);

        studentPW.student(null);
        assertThat(studentPW.getStudent()).isNull();
    }

    @Test
    void pwTest() throws Exception {
        StudentPW studentPW = getStudentPWRandomSampleGenerator();
        PW pWBack = getPWRandomSampleGenerator();

        studentPW.setPw(pWBack);
        assertThat(studentPW.getPw()).isEqualTo(pWBack);

        studentPW.pw(null);
        assertThat(studentPW.getPw()).isNull();
    }

    @Test
    void academicYearTest() throws Exception {
        StudentPW studentPW = getStudentPWRandomSampleGenerator();
        AcademicYear academicYearBack = getAcademicYearRandomSampleGenerator();

        studentPW.setAcademicYear(academicYearBack);
        assertThat(studentPW.getAcademicYear()).isEqualTo(academicYearBack);

        studentPW.academicYear(null);
        assertThat(studentPW.getAcademicYear()).isNull();
    }
}
