package org.obs.app.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import org.jboss.logmanager.Level;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(ExceptionHandler.class);

    @Override
    public Response toResponse(Exception e) {
        if(e instanceof UserNotFoundException) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(LocalDateTime.now(), Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND, e.getMessage()))
                    .build();
        } else if (e instanceof InvalidParameterException) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ApiError(LocalDateTime.now(), Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST, e.getMessage()))
                    .build();
        }
        LOG.error("An unexpected error occurred", e);
        
        return Response
                .serverError()
                .build();
    }

}