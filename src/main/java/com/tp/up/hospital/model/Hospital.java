package com.tp.up.hospital.model;

import java.util.ArrayList;

public class Hospital {
    private String name;
    private ArrayList<Doctor> doctors;
    private ArrayList<Consultation> consultations;
    private ArrayList<Pacient> pacients;

    public Hospital(String name, ArrayList<Doctor> doctors, ArrayList<Consultation> consultations, ArrayList<Pacient> pacients) {
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

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public ArrayList<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(ArrayList<Consultation> consultations) {
        this.consultations = consultations;
    }

    public ArrayList<Pacient> getPacients() {
        return pacients;
    }

    public void setPacients(ArrayList<Pacient> pacients) {
        this.pacients = pacients;
    }
}
