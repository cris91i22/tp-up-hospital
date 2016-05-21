package com.tp.up.hospital.model;

import java.util.ArrayList;

public class Doctor {

    private Integer enrollment;
    private String name;
    private ArrayList<Consultation> consultations;

    public Doctor(Integer enrollment, String name, ArrayList<Consultation> consultations) {
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

    public ArrayList<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(ArrayList<Consultation> consultations) {
        this.consultations = consultations;
    }
}
