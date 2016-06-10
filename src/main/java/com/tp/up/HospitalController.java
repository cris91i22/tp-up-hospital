package com.tp.up;

import com.tp.up.hospital.HospitalService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/service")
public class HospitalController {
	private static HospitalService c = new HospitalService();

    @POST
    @Path("/create/pacient/{param0}/{param1}/{param2}")
        public void crearPaciente(@PathParam("param0") java.lang.String param0, @PathParam("param1") java.lang.Integer param1, @PathParam("param2") java.lang.String param2) {
        c.crearPaciente(param0,param1,param2);
    }

    @GET
    @Path("/patients")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<com.tp.up.hospital.model.Pacient> getPatients() {
        return c.getPatients();
    }

    @DELETE
    @Path("/patient/{param0}")
        public void deletePatient(@PathParam("param0") java.lang.Integer param0) {
        c.deletePatient(param0);
    }

    @POST
    @Path("/create/consultation/{param0}/{param1}")
    @Produces(MediaType.APPLICATION_JSON)
    public java.lang.Integer crearConsulta(@PathParam("param0") java.lang.Integer param0, @PathParam("param1") java.lang.String param1) {
        return c.crearConsulta(param0,param1);
    }

    @GET
    @Path("/consultations")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<com.tp.up.hospital.model.Consultation> getConsultations() {
        return c.getConsultations();
    }

    @POST
    @Path("/atender/{param0}")
        public void atender(@PathParam("param0") java.lang.Integer param0) {
        c.atender(param0);
    }

    @POST
    @Path("/finalizar/{param0}")
        public void finalizarConsulta(@PathParam("param0") java.lang.Integer param0) {
        c.finalizarConsulta(param0);
    }
}