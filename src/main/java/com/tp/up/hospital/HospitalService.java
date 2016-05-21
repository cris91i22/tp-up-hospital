package com.tp.up.hospital;

import java.util.ArrayList;
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
	}
	
    @AccesMethod(type = "GET", path = "/init")
    public Hospital inicializarHospital() {
    	return hospital;
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
    public ArrayList<Pacient> deletePatients(Integer dni) {
    	ArrayList<Pacient> patients = hospital.getPacients();
    	for (Pacient pacient : patients) {
			if (pacient.getDni().equals(dni)) {
				hospital.getPacients().remove(pacient);
			}
		}
    	return hospital.getPacients();
    }
    
    @AccesMethod(type = "POST", path = "/create/consultation")
    public int crearConsulta(Integer dni, String tipoConsulta) {
    	
    	Iterator<Pacient> iterador = hospital.getPacients().iterator();
    	
    	while(iterador.hasNext()){
    		Pacient paciente = iterador.next();
    		
    		if (paciente.getDni() == dni) {
    			nroConsulta ++;
    			Consultation Consulta = new Consultation(nroConsulta, paciente, new Date(), tipoConsulta, new LinkedList<String>(), false); 
    			hospital.getConsultations().add(Consulta);
    		} else
					System.out.println("El Paciente no existe");
    	}
    	return nroConsulta;
    }
    
    @AccesMethod(type = "POST", path = "/atender")
    public boolean atender(Integer codConsulta) {
    	
    	Iterator<Doctor> itMedico = hospital.getDoctors().iterator();
    	
    	while(itMedico.hasNext()) {
    		Iterator<Consultation> itConsulta = itMedico.next().getConsultations().iterator();
    		
    		while(itConsulta.hasNext()) {
    			if (itConsulta.next().getFinished()) { 
    				itMedico.next().getConsultations().add(modificaConsulta(codConsulta));
    				return true;
    			}
    		}
    		
    	}
    	return false;
    }
    	
    @AccesMethod(type = "POST", path = "/finalizar")
    public void finalizarConsulta(int codConsulta) {
    	Iterator<Consultation> itConsulta = hospital.getConsultations().iterator();
    	boolean encontrado = false;
    	
    	while(itConsulta.hasNext()) {
    		if(itConsulta.next().getCodeConsultation() == codConsulta) {
    			itConsulta.next().setFinished(true);
    			encontrado = true;
    		}
    	}	
    	
    	if (!encontrado)
    		System.out.println("No se encontro la consulta");
    }
    
    private Consultation modificaConsulta(Integer codConsulta) {
    	
    	Iterator<Consultation> itConsulta = hospital.getConsultations().iterator();
    	
    	
    	while( itConsulta.hasNext()) {
    		Consultation Consulta_it = itConsulta.next();
    		
	    	if (Consulta_it.getCodeConsultation() == codConsulta) {
	    		Consulta_it.getMedicines().add("PARACETAMOL");
	    		Consulta_it.setStartDate(new Date());
	    		return Consulta_it;
	    	}
	    	else {
	    		System.out.println("La consulta no exite");
	    	}
    
    	}
		return null;
    }	
    /**
     *
     class HospitalService {

     var hospital: Hospital = null
     var nroConsulta = 0

     def crearHospital() = {
     val medicos = List(Doctor(123,"Juan", new mutable.MutableList()),
     Doctor(124,"Jorge", new mutable.MutableList()),
     Doctor(125,"Pablo", new mutable.MutableList()),
     Doctor(126,"Dario", new mutable.MutableList()),
     Doctor(127,"Roberto", new mutable.MutableList()))
     val pacientes = List(Pacient("Sofia",3334444,"OSDE"),
     Pacient("Martin",5553333,"OSDE"),
     Pacient("Leandro",9994444,"SWISS_MEDICAL"),
     Pacient("Norma",8884444,"OSDE"),
     Pacient("Juan",3337777,"OSECAC"),
     Pacient("Pedro",3332244,"OSDE"),
     Pacient("Romina",3338444,"ITALIANO"))


     hospital = Hospital(name = "Hospital 1",
     doctors = medicos,
     consultations = Nil,
     pacients = pacientes)
     }

     def crearPaciente(nombre: String, dni: Int, os: String) = {
     val pacNuevo = Pacient(nombre, dni, os)
     hospital.pacients = hospital.pacients :+ pacNuevo
     }

     def crearConsulta(dni: Int, tipoConsulta: String): Int = {
     val nroconsulta = hospital.pacients.find(p => p.dni == dni) match {
     case Some(r) =>
     nroConsulta = nroConsulta + 1
     val consulta = Consultation(nroConsulta, r, new Date, tipoConsulta, new mutable.MutableList(), false)
     hospital.consultations = hospital.consultations :+ consulta
     nroConsulta
     case None => throw new RuntimeException(s"El paciente con dni $dni no se encuentra registrado, necesita crearlo...")
     }

     nroconsulta
     }

     def atender(codConsulta: Int): Unit = {
     hospital.doctors.exists{ m =>
     m.consultations.forall(c => c.finished) match {
     case true => val consulta = modificaConsulta(codConsulta) ; m.consultations = m.consultations :+ consulta ; true
     case false => false
     }
     }
     }

     def mostraConsulta(codConsulta: Int): Consultation = {
     hospital.consultations.find(_.code == codConsulta) match {
     case Some(r) =>  r
     case None => throw new RuntimeException(s"No encontre la consulta nro $codConsulta")
     }
     }

     def finalizarConsulta(cod: Int) = {
     hospital.consultations.find(_.code == cod) match {
     case Some(r) =>  r.finished = true
     case None => throw new RuntimeException(s"No encontre la consulta nro $cod")
     }
     }

     private def modificaConsulta(cod: Int): Consultation = {
     hospital.consultations.find(_.code == cod) match {
     case Some(r) => r.medicines = r.medicines :+ Prescription.PARACETAMOL.toString ; r.startDate = new Date ; r
     case None => throw new RuntimeException(s"No encontre la consulta nro $cod")
     }
     }


     }

     *
     *
     *
     */
}
