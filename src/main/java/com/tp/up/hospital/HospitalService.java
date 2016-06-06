package com.tp.up.hospital;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import com.tp.up.hospital.exceptions.ExceptionConsultaNoExiste;
import com.tp.up.hospital.exceptions.ExceptionPacienteNoRegistrado;
import com.tp.up.hospital.model.*;

import com.tp.up.annotations.AccesMethod;

public class HospitalService {
	
	private Hospital hospital;
	private Integer nroConsulta;
    
	public HospitalService(){
		String nombreHospital = "Hospital Medina-Vidal";
    	ArrayList<Doctor> doctors = new ArrayList<Doctor>();
    	Doctor doc1 = new Doctor(123,"Jorge", new ArrayList<Consultation>());
    	Doctor doc2 = new Doctor(124,"Pablo", new ArrayList<Consultation>());
    	Doctor doc3 = new Doctor(125,"Dario", new ArrayList<Consultation>());
    	Doctor doc4 = new Doctor(126,"Juan", new ArrayList<Consultation>());
    	Doctor doc5 = new Doctor(127,"Roberto", new ArrayList<Consultation>());
    	doctors.add(doc1);
    	doctors.add(doc2);
    	doctors.add(doc3);
    	doctors.add(doc4);
    	doctors.add(doc5);
    	
    	ArrayList<Pacient> patients = new ArrayList<Pacient>();
    	Pacient p1 = new Pacient("Sofia",3334444,"OSDE");
		Pacient p2 = new Pacient("Martin",5553333,"OSDE");
		Pacient p3 = new Pacient("Leandro",9994444,"SWISS_MEDICAL");
		Pacient p4= new Pacient("Norma",8884444,"OSDE");
		Pacient p5 = new Pacient("Juan",3337777,"OSECAC");
		Pacient p6 = new Pacient("Pedro",3332244,"OSDE");
		Pacient p7 = new Pacient("Romina",3338444,"ITALIANO");
		patients.add(p1);
		patients.add(p2);
		patients.add(p3);
		patients.add(p4);
		patients.add(p5);
		patients.add(p6);
		patients.add(p7);
    	hospital = new Hospital(nombreHospital, doctors, new ArrayList<Consultation>(), patients);
    	nroConsulta = new Integer(0);
	}
	
    @AccesMethod(type = "POST", path = "/create/pacient")
    public void crearPaciente(String nombre, Integer dni, String obrasocial) {
    	ArrayList<Pacient> patients = hospital.getPacients();
    	patients.add(new Pacient(nombre, dni, obrasocial));
    }
    
    @AccesMethod(type = "GET", path = "/patients")
    public ArrayList<Pacient> getPatients() {
    	return hospital.getPacients();
    }
    
    @AccesMethod(type = "DELETE", path = "/patient")
    public void deletePatient(Integer dni) {
    	for (int i = 0; i < hospital.getPacients().size(); i++) {
    		if (hospital.getPacients().get(i).getDni().equals(dni)){
    			ArrayList<Pacient> patients = hospital.getPacients();
    			patients.remove(i);
    			hospital.setPacients(patients);
    		}
		}
    }
    
    @AccesMethod(type = "POST", path = "/create/consultation")
    public Integer crearConsulta(Integer dni, String tipoConsulta) {	
    	for (Pacient p : hospital.getPacients()) {
			if(p.getDni().equals(dni)){
				nroConsulta ++;
				Consultation consulta = new Consultation(nroConsulta, p, new Date(), tipoConsulta, new LinkedList<String>(), false); 
				hospital.getConsultations().add(consulta);
			}
		}
    	return nroConsulta;
    }
    
    @AccesMethod(type = "GET", path = "/consultations")
    public ArrayList<Consultation> getConsultations() {	
    	return hospital.getConsultations();
    }
    
    @AccesMethod(type = "POST", path = "/atender")
    public void atender(Integer codConsulta) {
    	Collections.shuffle(hospital.getDoctors());
		for (Consultation con : hospital.getConsultations()) {
			if (con.getCodeConsultation().equals(codConsulta) & !con.getFinished()){
				hospital.getDoctors().get(0).getConsultations().add(modificaConsulta(con));
			}
		}
    }
    	
    @AccesMethod(type = "POST", path = "/finalizar")
    public void finalizarConsulta(Integer codConsulta) {
    	boolean encontrado = false;
    	for (Consultation con : hospital.getConsultations()) {
    		if (con.getCodeConsultation().equals(codConsulta) & !con.getFinished()){
    			con.setFinished(true);
    			encontrado = true;
			}
		}
    	if (!encontrado)
    		System.out.println("No se encontro la consulta");
    }
    
    private Consultation modificaConsulta(Consultation cons) {
    	cons.getMedicines().add("PARACETAMOL");
    	cons.setStartDate(new Date());
    	return cons;
    }	
    
}
