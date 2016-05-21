package com.tp.up.hospital.model;

public class Pacient {

    private String name;
    private Integer dni;
    private String medicalAssurance;

    public Pacient(Integer dni){this.dni = dni;}
    
    public Pacient(String name, Integer dni, String medicalAssurance) {
        this.name = name;
        this.dni = dni;
        this.medicalAssurance = medicalAssurance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getMedicalAssurance() {
        return medicalAssurance;
    }

    public void setMedicalAssurance(String medicalAssurance) {
        this.medicalAssurance = medicalAssurance;
    }
}