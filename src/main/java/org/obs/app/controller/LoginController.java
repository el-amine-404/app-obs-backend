package org.obs.app.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.obs.app.dto.LoginDto;
import org.obs.app.dto.Message;
import org.obs.app.dto.TokenResponse;
import org.obs.app.model.User;
import org.obs.app.service.UserService;

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class LoginController {
    
    private final UserService userService;
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response login(@Valid final LoginDto loginDto) {
        if (userService.checkUserCredentials(loginDto.getUsername(), loginDto.getPassword())) {
            User user = userService.findByUsername(loginDto.getUsername());
            String token = userService.generateJwtToken(user);
            return Response
                    .ok()
                    .entity(new TokenResponse("Bearer " + token, userService.getJwtExpirationTime()))
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity(new Message("Invalid credentials")).build();
        }
    }

}
