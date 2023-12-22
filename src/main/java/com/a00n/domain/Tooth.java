package com.a00n.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tooth.
 */
@Entity
@Table(name = "tooth")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tooth implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tooth")
    @JsonIgnoreProperties(value = { "tooth", "academicYear", "groupes" }, allowSetters = true)
    private Set<PW> pws = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tooth id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Tooth name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PW> getPws() {
        return this.pws;
    }

    public void setPws(Set<PW> pWS) {
        if (this.pws != null) {
            this.pws.forEach(i -> i.setTooth(null));
        }
        if (pWS != null) {
            pWS.forEach(i -> i.setTooth(this));
        }
        this.pws = pWS;
    }

    public Tooth pws(Set<PW> pWS) {
        this.setPws(pWS);
        return this;
    }

    public Tooth addPw(PW pW) {
        this.pws.add(pW);
        pW.setTooth(this);
        return this;
    }

    public Tooth removePw(PW pW) {
        this.pws.remove(pW);
        pW.setTooth(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tooth)) {
            return false;
        }
        return getId() != null && getId().equals(((Tooth) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tooth{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
