package com.a00n.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A PW.
 */
@Entity
@Table(name = "pw")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PW implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "objectif")
    private String objectif;

    @Column(name = "docs")
    private String docs;

    @Column(name = "deadline")
    private Instant deadline;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "pws" }, allowSetters = true)
    private Tooth tooth;

    @ManyToOne(optional = false)
    @NotNull
    private AcademicYear academicYear;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_pw__groupe", joinColumns = @JoinColumn(name = "pw_id"), inverseJoinColumns = @JoinColumn(name = "groupe_id"))
    @JsonIgnoreProperties(value = { "professor", "academicYear", "students", "pws" }, allowSetters = true)
    private Set<Groupe> groupes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PW id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public PW title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObjectif() {
        return this.objectif;
    }

    public PW objectif(String objectif) {
        this.setObjectif(objectif);
        return this;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public String getDocs() {
        return this.docs;
    }

    public PW docs(String docs) {
        this.setDocs(docs);
        return this;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public Instant getDeadline() {
        return this.deadline;
    }

    public PW deadline(Instant deadline) {
        this.setDeadline(deadline);
        return this;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public Tooth getTooth() {
        return this.tooth;
    }

    public void setTooth(Tooth tooth) {
        this.tooth = tooth;
    }

    public PW tooth(Tooth tooth) {
        this.setTooth(tooth);
        return this;
    }

    public AcademicYear getAcademicYear() {
        return this.academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public PW academicYear(AcademicYear academicYear) {
        this.setAcademicYear(academicYear);
        return this;
    }

    public Set<Groupe> getGroupes() {
        return this.groupes;
    }

    public void setGroupes(Set<Groupe> groupes) {
        this.groupes = groupes;
    }

    public PW groupes(Set<Groupe> groupes) {
        this.setGroupes(groupes);
        return this;
    }

    public PW addGroupe(Groupe groupe) {
        this.groupes.add(groupe);
        return this;
    }

    public PW removeGroupe(Groupe groupe) {
        this.groupes.remove(groupe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PW)) {
            return false;
        }
        return getId() != null && getId().equals(((PW) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PW{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", objectif='" + getObjectif() + "'" +
            ", docs='" + getDocs() + "'" +
            ", deadline='" + getDeadline() + "'" +
            "}";
    }
}
