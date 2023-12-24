package com.a00n.service.dto;

public class GroupStudentCountDTO {

    private String name;
    private Long count;

    public GroupStudentCountDTO(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "GroupStudentCountDTO [name=" + name + ", count=" + count + "]";
    }
}
