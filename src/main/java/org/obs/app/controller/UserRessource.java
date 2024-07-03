package org.obs.app.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class UserRessource {

    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public String Hello() {
        return "Hello World !";
    }

}
