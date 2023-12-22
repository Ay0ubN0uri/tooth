package com.a00n.domain;

import static com.a00n.domain.GroupeTestSamples.*;
import static com.a00n.domain.ProfessorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.a00n.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProfessorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Professor.class);
        Professor professor1 = getProfessorSample1();
        Professor professor2 = new Professor();
        assertThat(professor1).isNotEqualTo(professor2);

        professor2.setId(professor1.getId());
        assertThat(professor1).isEqualTo(professor2);

        professor2 = getProfessorSample2();
        assertThat(professor1).isNotEqualTo(professor2);
    }

    @Test
    void groupeTest() throws Exception {
        Professor professor = getProfessorRandomSampleGenerator();
        Groupe groupeBack = getGroupeRandomSampleGenerator();

        professor.addGroupe(groupeBack);
        assertThat(professor.getGroupes()).containsOnly(groupeBack);
        assertThat(groupeBack.getProfessor()).isEqualTo(professor);

        professor.removeGroupe(groupeBack);
        assertThat(professor.getGroupes()).doesNotContain(groupeBack);
        assertThat(groupeBack.getProfessor()).isNull();

        professor.groupes(new HashSet<>(Set.of(groupeBack)));
        assertThat(professor.getGroupes()).containsOnly(groupeBack);
        assertThat(groupeBack.getProfessor()).isEqualTo(professor);

        professor.setGroupes(new HashSet<>());
        assertThat(professor.getGroupes()).doesNotContain(groupeBack);
        assertThat(groupeBack.getProfessor()).isNull();
    }
}
