package com.a00n.service.dto;

import com.a00n.domain.Professor;
import com.a00n.domain.User;

public class ProfessorDTO extends AdminUserDTO {

    public ProfessorDTO() {
        super();
    }

    public ProfessorDTO(User user, Professor professor) {
        super(user);
        this.grade = professor.getGrade();
    }

    private String grade;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.grade;
    }
}
