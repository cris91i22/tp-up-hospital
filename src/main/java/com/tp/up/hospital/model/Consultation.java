package com.tp.up.hospital.model;

import java.util.Date;
import java.util.List;

public class Consultation {
    private Integer codeConsultation;
    private Pacient pacient;
    private Date startDate;
    private String consultType;
    private List<String> medicines;
    private Boolean finished;


    public Consultation(Integer codeConsultation, Pacient pacient, Date startDate, String consultType, List<String> medicines, Boolean finished) {
        this.codeConsultation = codeConsultation;
        this.pacient = pacient;
        this.startDate = startDate;
        this.consultType = consultType;
        this.medicines = medicines;
        this.finished = finished;
    }

    public Integer getCodeConsultation() {
        return codeConsultation;
    }

    public void setCodeConsultation(Integer codeConsultation) {
        this.codeConsultation = codeConsultation;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getConsultType() {
        return consultType;
    }

    public void setConsultType(String consultType) {
        this.consultType = consultType;
    }

    public List<String> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<String> medicines) {
        this.medicines = medicines;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
