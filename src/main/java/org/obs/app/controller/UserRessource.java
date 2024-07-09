package org.obs.app.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.obs.app.model.User;
import org.obs.app.service.UserService;

import java.util.List;

@Path("/api/v1")
public class UserRessource {


    private final UserService userService;

    public UserRessource(UserService userService){
        this.userService = userService;
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello World !";
    }

    @Operation(summary = "Get a user by id", description = "Returns a user as per the id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully retrieved")
    })
    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userId") @Parameter(name="id", description="user id", example="1") Long userId) {
        return Response
                .ok(userService.getUser(userId))
                .build();
    }

    @Operation(summary = "Get all users", description = "Returns all users")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully retrieved")
    })
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        return Response
                .ok(userService.getUsers())
                .build();
    }

}
