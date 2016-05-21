package com.tp.up;

import com.tp.up.hospital.HospitalService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
@Path("/service")
public class HospitalController {

	HospitalService c = new HospitalService();
	
    @GET
    @Path("/init")
    @Produces(MediaType.APPLICATION_JSON)
    public com.tp.up.hospital.model.Hospital inicializarHospital() {
        return c.inicializarHospital();
    }

    @POST
    @Path("/create/pacient/{param0}/{param1}/{param2}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void crearPaciente(@PathParam("param0") java.lang.String param0, @PathParam("param1") java.lang.Integer param1, @PathParam("param2") java.lang.String param2) {
        c.crearPaciente(param0,param1,param2);
    }

    @GET
    @Path("/patients")
    @Produces(MediaType.APPLICATION_JSON)
    public java.util.ArrayList getPatients() {
        return c.getPatients();
    }

    @DELETE
    @Path("/patient/{param0}")
    @Produces(MediaType.APPLICATION_JSON)
    public java.util.ArrayList deletePatients(@PathParam("param0") java.lang.Integer param0) {
        return c.deletePatients(param0);
    }

    @POST
    @Path("/create/consultation/{param0}/{param1}")
    @Consumes(MediaType.APPLICATION_JSON)
    public int crearConsulta(@PathParam("param0") java.lang.Integer param0, @PathParam("param1") java.lang.String param1) {
        return c.crearConsulta(param0,param1);
    }

    @POST
    @Path("/atender/{param0}")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean atender(@PathParam("param0") java.lang.Integer param0) {
        return c.atender(param0);
    }

    @POST
    @Path("/finalizar/{param0}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void finalizarConsulta(@PathParam("param0") int param0) {
        c.finalizarConsulta(param0);
    }
}