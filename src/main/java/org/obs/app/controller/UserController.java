package org.obs.app.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.obs.app.model.User;
import org.obs.app.service.UserService;

@Path("/api/v1/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @Operation(summary = "Get all users", description = "Returns all users")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully retrieved")
    })
    @GET
    public Response getUsers() {
        return Response
                .ok(userService.getUsers())
                .build();
    }

    @Operation(summary = "Get a user by id", description = "Returns a user as per the id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully retrieved")
    })
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") @Parameter(name="id", description="user id", example="1") Long userId) {
        return Response
                .ok(userService.getUser(userId))
                .build();
    }

    @POST
    public Response create(User user) {
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response update(@PathParam("id") long userId) {
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response replace(@PathParam("id") long userId, User user) {
        return Response.noContent().build();
    }

}
