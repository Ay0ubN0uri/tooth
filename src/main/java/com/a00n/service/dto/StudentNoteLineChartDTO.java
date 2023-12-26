package com.a00n.service.dto;

import java.util.List;

public class StudentNoteLineChartDTO {

    private List<String> labels;
    private List<StudentLineChartDTO> datasets;

    public StudentNoteLineChartDTO(List<String> labels, List<StudentLineChartDTO> datasets) {
        this.labels = labels;
        this.datasets = datasets;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<StudentLineChartDTO> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<StudentLineChartDTO> datasets) {
        this.datasets = datasets;
    }

    @Override
    public String toString() {
        return "StudentNoteLineChartDTO [labels=" + labels + ", datasets=" + datasets + "]";
    }
}
