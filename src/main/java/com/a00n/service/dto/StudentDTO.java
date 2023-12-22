package com.a00n.service.dto;

import com.a00n.domain.Student;
import com.a00n.domain.User;
import java.time.Instant;

public class StudentDTO extends AdminUserDTO {

    private String number;
    private String cne;
    private String cin;
    private Instant birthDay;

    public StudentDTO() {
        super();
    }

    public StudentDTO(User user, Student student) {
        super(user);
        this.number = student.getNumber();
        this.cne = student.getCne();
        this.cin = student.getCin();
        this.birthDay = student.getBirthDay();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public Instant getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Instant birthDay) {
        this.birthDay = birthDay;
    }
}
