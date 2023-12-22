package com.a00n.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private String number;

    @NotNull
    @Column(name = "cne", nullable = false)
    private String cne;

    @NotNull
    @Column(name = "cin", nullable = false)
    private String cin;

    @NotNull
    @Column(name = "birth_day", nullable = false)
    private Instant birthDay;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_student__groupe",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "groupe_id")
    )
    @JsonIgnoreProperties(value = { "professor", "academicYear", "students", "pws" }, allowSetters = true)
    private Set<Groupe> groupes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Student id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public Student number(String number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCne() {
        return this.cne;
    }

    public Student cne(String cne) {
        this.setCne(cne);
        return this;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public String getCin() {
        return this.cin;
    }

    public Student cin(String cin) {
        this.setCin(cin);
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public Instant getBirthDay() {
        return this.birthDay;
    }

    public Student birthDay(Instant birthDay) {
        this.setBirthDay(birthDay);
        return this;
    }

    public void setBirthDay(Instant birthDay) {
        this.birthDay = birthDay;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Student user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Groupe> getGroupes() {
        return this.groupes;
    }

    public void setGroupes(Set<Groupe> groupes) {
        this.groupes = groupes;
    }

    public Student groupes(Set<Groupe> groupes) {
        this.setGroupes(groupes);
        return this;
    }

    public Student addGroupe(Groupe groupe) {
        this.groupes.add(groupe);
        return this;
    }

    public Student removeGroupe(Groupe groupe) {
        this.groupes.remove(groupe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return getId() != null && getId().equals(((Student) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", cne='" + getCne() + "'" +
            ", cin='" + getCin() + "'" +
            ", birthDay='" + getBirthDay() + "'" +
            "}";
    }
}
