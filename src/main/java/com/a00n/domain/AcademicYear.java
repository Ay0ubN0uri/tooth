package com.a00n.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A AcademicYear.
 */
@Entity
@Table(name = "academic_year")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AcademicYear implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "year")
    private String year;

    @Column(name = "starting_date")
    private Instant startingDate;

    @Column(name = "ending_date")
    private Instant endingDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AcademicYear id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return this.year;
    }

    public AcademicYear year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Instant getStartingDate() {
        return this.startingDate;
    }

    public AcademicYear startingDate(Instant startingDate) {
        this.setStartingDate(startingDate);
        return this;
    }

    public void setStartingDate(Instant startingDate) {
        this.startingDate = startingDate;
    }

    public Instant getEndingDate() {
        return this.endingDate;
    }

    public AcademicYear endingDate(Instant endingDate) {
        this.setEndingDate(endingDate);
        return this;
    }

    public void setEndingDate(Instant endingDate) {
        this.endingDate = endingDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcademicYear)) {
            return false;
        }
        return getId() != null && getId().equals(((AcademicYear) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcademicYear{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            ", startingDate='" + getStartingDate() + "'" +
            ", endingDate='" + getEndingDate() + "'" +
            "}";
    }
}
