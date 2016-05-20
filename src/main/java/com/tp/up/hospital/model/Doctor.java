package com.tp.up.hospital.model;

import java.util.List;

public class Doctor {

    private Integer enrollment;
    private String name;
    private List<Consultation> consultations;


    public Doctor(Integer enrollment, String name, List<Consultation> consultations) {
        this.enrollment = enrollment;
        this.name = name;
        this.consultations = consultations;
    }

    public Integer getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Integer enrollment) {
        this.enrollment = enrollment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
}
