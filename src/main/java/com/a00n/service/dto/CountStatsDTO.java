package com.a00n.service.dto;

public class CountStatsDTO {

    private Long studentCount;
    private Long groupCount;
    private Long professorCount;
    private Long pwsCount;

    public CountStatsDTO(Long studentCount, Long groupCount, Long professorCount, Long pwsCount) {
        this.studentCount = studentCount;
        this.groupCount = groupCount;
        this.professorCount = professorCount;
        this.pwsCount = pwsCount;
    }

    public Long getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Long studentCount) {
        this.studentCount = studentCount;
    }

    public Long getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Long groupCount) {
        this.groupCount = groupCount;
    }

    public Long getProfessorCount() {
        return professorCount;
    }

    public void setProfessorCount(Long professorCount) {
        this.professorCount = professorCount;
    }

    public Long getPwsCount() {
        return pwsCount;
    }

    public void setPwsCount(Long pwsCount) {
        this.pwsCount = pwsCount;
    }
}
