package com.a00n.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A StudentPW.
 */
@Entity
@Table(name = "student_pw")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StudentPW implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "time")
    private String time;

    @Lob
    @Column(name = "image_front")
    private byte[] imageFront;

    @Column(name = "image_front_content_type")
    private String imageFrontContentType;

    @Lob
    @Column(name = "image_side")
    private byte[] imageSide;

    @Column(name = "image_side_content_type")
    private String imageSideContentType;

    @Column(name = "date")
    private Instant date;

    @Column(name = "note")
    private Double note;

    @Column(name = "a_1_image_side")
    private Double a1ImageSide;

    @Column(name = "a_2_image_side")
    private Double a2ImageSide;

    @Column(name = "a_3_image_side")
    private Double a3ImageSide;

    @Column(name = "p_1_image_side")
    private Double p1ImageSide;

    @Column(name = "p_2_image_side")
    private Double p2ImageSide;

    @Column(name = "p_3_image_side")
    private Double p3ImageSide;

    @Column(name = "a_1_image_front")
    private Double a1ImageFront;

    @Column(name = "a_2_image_front")
    private Double a2ImageFront;

    @Column(name = "a_3_image_front")
    private Double a3ImageFront;

    @Column(name = "p_1_image_front")
    private Double p1ImageFront;

    @Column(name = "p_2_image_front")
    private Double p2ImageFront;

    @Column(name = "p_3_image_front")
    private Double p3ImageFront;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "groupes" }, allowSetters = true)
    private Student student;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "tooth", "academicYear", "groupes" }, allowSetters = true)
    private PW pw;

    @ManyToOne(optional = false)
    @NotNull
    private AcademicYear academicYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StudentPW id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return this.time;
    }

    public StudentPW time(String time) {
        this.setTime(time);
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public byte[] getImageFront() {
        return this.imageFront;
    }

    public StudentPW imageFront(byte[] imageFront) {
        this.setImageFront(imageFront);
        return this;
    }

    public void setImageFront(byte[] imageFront) {
        this.imageFront = imageFront;
    }

    public String getImageFrontContentType() {
        return this.imageFrontContentType;
    }

    public StudentPW imageFrontContentType(String imageFrontContentType) {
        this.imageFrontContentType = imageFrontContentType;
        return this;
    }

    public void setImageFrontContentType(String imageFrontContentType) {
        this.imageFrontContentType = imageFrontContentType;
    }

    public byte[] getImageSide() {
        return this.imageSide;
    }

    public StudentPW imageSide(byte[] imageSide) {
        this.setImageSide(imageSide);
        return this;
    }

    public void setImageSide(byte[] imageSide) {
        this.imageSide = imageSide;
    }

    public String getImageSideContentType() {
        return this.imageSideContentType;
    }

    public StudentPW imageSideContentType(String imageSideContentType) {
        this.imageSideContentType = imageSideContentType;
        return this;
    }

    public void setImageSideContentType(String imageSideContentType) {
        this.imageSideContentType = imageSideContentType;
    }

    public Instant getDate() {
        return this.date;
    }

    public StudentPW date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getNote() {
        return this.note;
    }

    public StudentPW note(Double note) {
        this.setNote(note);
        return this;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public Double geta1ImageSide() {
        return this.a1ImageSide;
    }

    public StudentPW a1ImageSide(Double a1ImageSide) {
        this.seta1ImageSide(a1ImageSide);
        return this;
    }

    public void seta1ImageSide(Double a1ImageSide) {
        this.a1ImageSide = a1ImageSide;
    }

    public Double geta2ImageSide() {
        return this.a2ImageSide;
    }

    public StudentPW a2ImageSide(Double a2ImageSide) {
        this.seta2ImageSide(a2ImageSide);
        return this;
    }

    public void seta2ImageSide(Double a2ImageSide) {
        this.a2ImageSide = a2ImageSide;
    }

    public Double geta3ImageSide() {
        return this.a3ImageSide;
    }

    public StudentPW a3ImageSide(Double a3ImageSide) {
        this.seta3ImageSide(a3ImageSide);
        return this;
    }

    public void seta3ImageSide(Double a3ImageSide) {
        this.a3ImageSide = a3ImageSide;
    }

    public Double getp1ImageSide() {
        return this.p1ImageSide;
    }

    public StudentPW p1ImageSide(Double p1ImageSide) {
        this.setp1ImageSide(p1ImageSide);
        return this;
    }

    public void setp1ImageSide(Double p1ImageSide) {
        this.p1ImageSide = p1ImageSide;
    }

    public Double getp2ImageSide() {
        return this.p2ImageSide;
    }

    public StudentPW p2ImageSide(Double p2ImageSide) {
        this.setp2ImageSide(p2ImageSide);
        return this;
    }

    public void setp2ImageSide(Double p2ImageSide) {
        this.p2ImageSide = p2ImageSide;
    }

    public Double getp3ImageSide() {
        return this.p3ImageSide;
    }

    public StudentPW p3ImageSide(Double p3ImageSide) {
        this.setp3ImageSide(p3ImageSide);
        return this;
    }

    public void setp3ImageSide(Double p3ImageSide) {
        this.p3ImageSide = p3ImageSide;
    }

    public Double geta1ImageFront() {
        return this.a1ImageFront;
    }

    public StudentPW a1ImageFront(Double a1ImageFront) {
        this.seta1ImageFront(a1ImageFront);
        return this;
    }

    public void seta1ImageFront(Double a1ImageFront) {
        this.a1ImageFront = a1ImageFront;
    }

    public Double geta2ImageFront() {
        return this.a2ImageFront;
    }

    public StudentPW a2ImageFront(Double a2ImageFront) {
        this.seta2ImageFront(a2ImageFront);
        return this;
    }

    public void seta2ImageFront(Double a2ImageFront) {
        this.a2ImageFront = a2ImageFront;
    }

    public Double geta3ImageFront() {
        return this.a3ImageFront;
    }

    public StudentPW a3ImageFront(Double a3ImageFront) {
        this.seta3ImageFront(a3ImageFront);
        return this;
    }

    public void seta3ImageFront(Double a3ImageFront) {
        this.a3ImageFront = a3ImageFront;
    }

    public Double getp1ImageFront() {
        return this.p1ImageFront;
    }

    public StudentPW p1ImageFront(Double p1ImageFront) {
        this.setp1ImageFront(p1ImageFront);
        return this;
    }

    public void setp1ImageFront(Double p1ImageFront) {
        this.p1ImageFront = p1ImageFront;
    }

    public Double getp2ImageFront() {
        return this.p2ImageFront;
    }

    public StudentPW p2ImageFront(Double p2ImageFront) {
        this.setp2ImageFront(p2ImageFront);
        return this;
    }

    public void setp2ImageFront(Double p2ImageFront) {
        this.p2ImageFront = p2ImageFront;
    }

    public Double getp3ImageFront() {
        return this.p3ImageFront;
    }

    public StudentPW p3ImageFront(Double p3ImageFront) {
        this.setp3ImageFront(p3ImageFront);
        return this;
    }

    public void setp3ImageFront(Double p3ImageFront) {
        this.p3ImageFront = p3ImageFront;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentPW student(Student student) {
        this.setStudent(student);
        return this;
    }

    public PW getPw() {
        return this.pw;
    }

    public void setPw(PW pW) {
        this.pw = pW;
    }

    public StudentPW pw(PW pW) {
        this.setPw(pW);
        return this;
    }

    public AcademicYear getAcademicYear() {
        return this.academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public StudentPW academicYear(AcademicYear academicYear) {
        this.setAcademicYear(academicYear);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentPW)) {
            return false;
        }
        return getId() != null && getId().equals(((StudentPW) o).getId());
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentPW{" +
                "id=" + getId() +
                ", time='" + getTime() + "'" +
                ", imageFront='" + getImageFront() + "'" +
                ", imageFrontContentType='" + getImageFrontContentType() + "'" +
                ", imageSide='" + getImageSide() + "'" +
                ", imageSideContentType='" + getImageSideContentType() + "'" +
                ", date='" + getDate() + "'" +
                ", note=" + getNote() +
                ", a1ImageSide=" + geta1ImageSide() +
                ", a2ImageSide=" + geta2ImageSide() +
                ", a3ImageSide=" + geta3ImageSide() +
                ", p1ImageSide=" + getp1ImageSide() +
                ", p2ImageSide=" + getp2ImageSide() +
                ", p3ImageSide=" + getp3ImageSide() +
                ", a1ImageFront=" + geta1ImageFront() +
                ", a2ImageFront=" + geta2ImageFront() +
                ", a3ImageFront=" + geta3ImageFront() +
                ", p1ImageFront=" + getp1ImageFront() +
                ", p2ImageFront=" + getp2ImageFront() +
                ", p3ImageFront=" + getp3ImageFront() +
                "}";
    }
}
