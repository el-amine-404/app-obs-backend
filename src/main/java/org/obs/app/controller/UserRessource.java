package org.obs.app.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.obs.app.model.User;
import org.obs.app.service.UserService;

@Path("/")
public class UserRessource {

    @Inject
    UserService userService;

    @GET
    @Path("user")
    @Produces(MediaType.TEXT_PLAIN)
    public String Hello() {
        return "Hello World !";
    }

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(Integer userId) {
        return userService.getUser(userId);
    }

}
