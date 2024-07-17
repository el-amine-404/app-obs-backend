package org.obs.app.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        if(e instanceof UserNotFoundException) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(LocalDateTime.now(), Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND, e.getMessage()))
                    .build();
        }
        return Response
                .serverError()
                .build();
    }
    
}
