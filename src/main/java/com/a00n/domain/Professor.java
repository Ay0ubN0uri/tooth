package com.a00n.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Professor.
 */
@Entity
@Table(name = "professor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Professor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "grade")
    private String grade;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
    @JsonIgnoreProperties(value = { "professor", "academicYear", "students", "pws" }, allowSetters = true)
    private Set<Groupe> groupes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Professor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrade() {
        return this.grade;
    }

    public Professor grade(String grade) {
        this.setGrade(grade);
        return this;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Professor user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Groupe> getGroupes() {
        return this.groupes;
    }

    public void setGroupes(Set<Groupe> groupes) {
        if (this.groupes != null) {
            this.groupes.forEach(i -> i.setProfessor(null));
        }
        if (groupes != null) {
            groupes.forEach(i -> i.setProfessor(this));
        }
        this.groupes = groupes;
    }

    public Professor groupes(Set<Groupe> groupes) {
        this.setGroupes(groupes);
        return this;
    }

    public Professor addGroupe(Groupe groupe) {
        this.groupes.add(groupe);
        groupe.setProfessor(this);
        return this;
    }

    public Professor removeGroupe(Groupe groupe) {
        this.groupes.remove(groupe);
        groupe.setProfessor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Professor)) {
            return false;
        }
        return getId() != null && getId().equals(((Professor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Professor{" +
            "id=" + getId() +
            ", grade='" + getGrade() + "'" +
            "}";
    }
}
