package com.tp.up;

import com.tp.up.hospital.HospitalService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/service")
public class HospitalController {
	
    @POST
    @Path("/create/pacient/{param0}/{param1}/{param2}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createPacient(@PathParam("param0") java.lang.String param0, @PathParam("param1") java.lang.Integer param1, @PathParam("param2") java.lang.String param2) {
        HospitalService c = new HospitalService();
        c.createPacient(param0,param1,param2);
    }
    
}