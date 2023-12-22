package com.a00n.domain;

import static com.a00n.domain.GroupeTestSamples.*;
import static com.a00n.domain.StudentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.a00n.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Student.class);
        Student student1 = getStudentSample1();
        Student student2 = new Student();
        assertThat(student1).isNotEqualTo(student2);

        student2.setId(student1.getId());
        assertThat(student1).isEqualTo(student2);

        student2 = getStudentSample2();
        assertThat(student1).isNotEqualTo(student2);
    }

    @Test
    void groupeTest() throws Exception {
        Student student = getStudentRandomSampleGenerator();
        Groupe groupeBack = getGroupeRandomSampleGenerator();

        student.addGroupe(groupeBack);
        assertThat(student.getGroupes()).containsOnly(groupeBack);

        student.removeGroupe(groupeBack);
        assertThat(student.getGroupes()).doesNotContain(groupeBack);

        student.groupes(new HashSet<>(Set.of(groupeBack)));
        assertThat(student.getGroupes()).containsOnly(groupeBack);

        student.setGroupes(new HashSet<>());
        assertThat(student.getGroupes()).doesNotContain(groupeBack);
    }
}
