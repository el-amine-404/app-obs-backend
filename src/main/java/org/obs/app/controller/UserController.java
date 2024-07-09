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

import javax.naming.directory.InvalidAttributesException;
import java.security.InvalidParameterException;
import java.util.Map;

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
        try {
            return Response
                    .ok(userService.getUsers())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Get a user by id", description = "Returns a user as per the id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully retrieved")
    })
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") @Parameter(name="id", description="user id", example="1") Long id) {
        try {
            final var userByIdOpt = userService.findUserById(id);
            if (userByIdOpt.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            return Response.ok(userByIdOpt.get()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    public Response create(User user) {
        try {
            userService.create(user);
            return Response.noContent().build();
        } catch (Exception e) {
            if (e instanceof InvalidAttributesException) {
                return Response.status(Response.Status.CONFLICT).entity(Map.of("message", e.getMessage())).build();
            }

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") long userId, User user) {
        try {
            return Response.ok(userService.update(userId, user)).build();
        } catch (Exception e) {
            if (e instanceof InvalidParameterException) {
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("message", e.getMessage())).build();
            }

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long userId) {
        var isDeleted = userService.delete(userId);
        if (!isDeleted) {
            return Response.notModified().build();
        }
        return Response.noContent().build();
    }

}
