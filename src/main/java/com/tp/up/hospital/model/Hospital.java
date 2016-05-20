package com.tp.up.hospital.model;

import java.util.List;

public class Hospital {
    private String name;
    private List<Doctor> doctors;
    private List<Consultation> consultations;
    private List<Pacient> pacients;

    public Hospital(String name, List<Doctor> doctors, List<Consultation> consultations, List<Pacient> pacients) {
        this.name = name;
        this.doctors = doctors;
        this.consultations = consultations;
        this.pacients = pacients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public List<Pacient> getPacients() {
        return pacients;
    }

    public void setPacients(List<Pacient> pacients) {
        this.pacients = pacients;
    }
}
