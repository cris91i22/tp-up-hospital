package com.tp.up.hospital;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.tp.up.hospital.exceptions.ExceptionConsultaNoExiste;
import com.tp.up.hospital.exceptions.ExceptionPacienteNoRegistrado;
import com.tp.up.hospital.model.*;

import com.tp.up.annotations.AccesMethod;

public class HospitalService {
	
	public Hospital hospital;

    @AccesMethod(type = "POST", path = "/create/pacient")
    public void createPacient(String name, Integer dni, String os){
     Pacient p = new Pacient(name,dni,os);
     hospital.getPacients().add(p);
    }

    private int nroConsulta;
    private List<Pacient> Pacientes;
    private List<Doctor> Medicos;
    private List<Consultation> Consultas;
    private String nombreHospital;
    
    public void crearHospital() {
    	this.hospital = new Hospital(this.nombreHospital, this.Medicos, this.Consultas, this.Pacientes);
    }
    
    public void crearPaciente(String nombre, int dni, String obrasocial) {
    	this.hospital.getPacients().add(new Pacient(nombre, dni, obrasocial));
    }
    
    public int crearConsulta(int dni, String tipoConsulta, Date fecha) throws ExceptionPacienteNoRegistrado {
    	
    	Iterator<Pacient> iterador = this.hospital.getPacients().iterator();
    	
    	while(iterador.hasNext()){
    		Pacient paciente = iterador.next();
    		
    		if (paciente.getDni() == dni) {
    			this.nroConsulta ++;
    			Consultation Consulta = new Consultation(this.nroConsulta, iterador.next(), fecha, tipoConsulta, new LinkedList<String>(), false); 
    			this.hospital.getConsultations().add(Consulta);
    		} else
					throw new ExceptionPacienteNoRegistrado("El Paciente no existe");
    	}
    	return this.nroConsulta;
    }
    
    public boolean atender(int codConsulta, Date fecha) throws ExceptionConsultaNoExiste {
    	
    	Iterator<Doctor> itMedico = this.hospital.getDoctors().iterator();
    	
    	while(itMedico.hasNext()) {
    		Iterator<Consultation> itConsulta = itMedico.next().getConsultations().iterator();
    		
    		while(itConsulta.hasNext()) {
    			if (itConsulta.next().getFinished()) { 
    				itMedico.next().getConsultations().add(modificaConsulta(codConsulta, fecha));
    				return true;
    			}
    		}
    		
    	}
    	return false;
    }
    
    private Consultation modificaConsulta(int codConsulta, Date fecha) throws ExceptionConsultaNoExiste {
    	
    	Iterator<Consultation> itConsulta = this.hospital.getConsultations().iterator();
    	
    	
    	while( itConsulta.hasNext()) {
    		Consultation Consulta_it = itConsulta.next();
    		
	    	if (Consulta_it.getCodeConsultation() == codConsulta) {
	    		Consulta_it.getMedicines().add("PARACETAMOL");
	    		Consulta_it.setStartDate(fecha);
	    		return Consulta_it;
	    	}
	    	else {
	    		throw new ExceptionConsultaNoExiste("La consulta no exite");
	    	}
    
    	}
		return null;
    }	
    	
    public void finalizarConsulta(int codConsulta) throws ExceptionConsultaNoExiste {
    	Iterator<Consultation> itConsulta = this.hospital.getConsultations().iterator();
    	boolean encontrado = false;
    	
    	while(itConsulta.hasNext()) {
    		if(itConsulta.next().getCodeConsultation() == codConsulta) {
    			itConsulta.next().setFinished(true);
    			encontrado = true;
    		}
    	}	
    	
    	if (!encontrado)
    		throw new ExceptionConsultaNoExiste("No se encontro la consulta");
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
