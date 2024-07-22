package org.obs.app.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.obs.app.dto.UserDto;
import org.obs.app.dto.UserUpdateCreateDto;
import org.obs.app.service.UserService;

import java.net.URI;

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
    @RolesAllowed({"ADMIN"})
    public Response getUsers() {
            return Response
                    .ok(userService.getUsers())
                    .build();

    }

    @Operation(summary = "Get a user by id", description = "Returns a user as per the id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully retrieved"),
            @APIResponse(responseCode = "404", description = "User not found")
    })
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") @Parameter(name="id", description="user id", example="1") Long userId) {
        return Response
                .ok(userService.getUser(userId))
                .build();
    }

    @Operation(summary = "Create a user", description = "Create a new user")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "User created"),
            @APIResponse(responseCode = "400", description = "Invalid input")
    })
    @POST
    @RolesAllowed({"ADMIN"})
    public Response create(@Valid @RequestBody UserUpdateCreateDto userDetails) {
        
            UserDto createdUser =  userService.create(userDetails);

            URI location = UriBuilder.fromResource(UserController.class)
                    .path("{id}")
                    .resolveTemplate("id", createdUser.getId())
                    .build();

            return Response
                    .created(location)
                    .entity(createdUser)
                    .build();

    }

    @Operation(summary = "Update a user", description = "Update a user")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "User updated"),
            @APIResponse(responseCode = "400", description = "Invalid input"),
            @APIResponse(responseCode = "404", description = "User not found"),
    })

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    public Response update(@PathParam("id") long userId, @Valid @RequestBody UserUpdateCreateDto userDetails) {
            return Response
                    .ok(userService.update(userId, userDetails))
                    .build();
    }

    @Operation(summary = "Delete a user", description = "Delete a user")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "User deleted"),
            @APIResponse(responseCode = "404", description = "User not found"),
    })
    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    public Response delete(@PathParam("id") long userId) {
        userService.delete(userId);
        return Response
                .noContent()
                .build();
    }

    
}
