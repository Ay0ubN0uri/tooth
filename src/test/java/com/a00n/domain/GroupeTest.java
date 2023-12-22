package com.a00n.domain;

import static com.a00n.domain.AcademicYearTestSamples.*;
import static com.a00n.domain.GroupeTestSamples.*;
import static com.a00n.domain.PWTestSamples.*;
import static com.a00n.domain.ProfessorTestSamples.*;
import static com.a00n.domain.StudentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.a00n.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GroupeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Groupe.class);
        Groupe groupe1 = getGroupeSample1();
        Groupe groupe2 = new Groupe();
        assertThat(groupe1).isNotEqualTo(groupe2);

        groupe2.setId(groupe1.getId());
        assertThat(groupe1).isEqualTo(groupe2);

        groupe2 = getGroupeSample2();
        assertThat(groupe1).isNotEqualTo(groupe2);
    }

    @Test
    void professorTest() throws Exception {
        Groupe groupe = getGroupeRandomSampleGenerator();
        Professor professorBack = getProfessorRandomSampleGenerator();

        groupe.setProfessor(professorBack);
        assertThat(groupe.getProfessor()).isEqualTo(professorBack);

        groupe.professor(null);
        assertThat(groupe.getProfessor()).isNull();
    }

    @Test
    void academicYearTest() throws Exception {
        Groupe groupe = getGroupeRandomSampleGenerator();
        AcademicYear academicYearBack = getAcademicYearRandomSampleGenerator();

        groupe.setAcademicYear(academicYearBack);
        assertThat(groupe.getAcademicYear()).isEqualTo(academicYearBack);

        groupe.academicYear(null);
        assertThat(groupe.getAcademicYear()).isNull();
    }

    @Test
    void studentTest() throws Exception {
        Groupe groupe = getGroupeRandomSampleGenerator();
        Student studentBack = getStudentRandomSampleGenerator();

        groupe.addStudent(studentBack);
        assertThat(groupe.getStudents()).containsOnly(studentBack);
        assertThat(studentBack.getGroupes()).containsOnly(groupe);

        groupe.removeStudent(studentBack);
        assertThat(groupe.getStudents()).doesNotContain(studentBack);
        assertThat(studentBack.getGroupes()).doesNotContain(groupe);

        groupe.students(new HashSet<>(Set.of(studentBack)));
        assertThat(groupe.getStudents()).containsOnly(studentBack);
        assertThat(studentBack.getGroupes()).containsOnly(groupe);

        groupe.setStudents(new HashSet<>());
        assertThat(groupe.getStudents()).doesNotContain(studentBack);
        assertThat(studentBack.getGroupes()).doesNotContain(groupe);
    }

    @Test
    void pwTest() throws Exception {
        Groupe groupe = getGroupeRandomSampleGenerator();
        PW pWBack = getPWRandomSampleGenerator();

        groupe.addPw(pWBack);
        assertThat(groupe.getPws()).containsOnly(pWBack);
        assertThat(pWBack.getGroupes()).containsOnly(groupe);

        groupe.removePw(pWBack);
        assertThat(groupe.getPws()).doesNotContain(pWBack);
        assertThat(pWBack.getGroupes()).doesNotContain(groupe);

        groupe.pws(new HashSet<>(Set.of(pWBack)));
        assertThat(groupe.getPws()).containsOnly(pWBack);
        assertThat(pWBack.getGroupes()).containsOnly(groupe);

        groupe.setPws(new HashSet<>());
        assertThat(groupe.getPws()).doesNotContain(pWBack);
        assertThat(pWBack.getGroupes()).doesNotContain(groupe);
    }
}
