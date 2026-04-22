package org.w2105627_coursework;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class CatchAllExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        ErrorMessage errorMessage = new ErrorMessage("Internal Server Error",500, "Unexpected Error Occured");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
    }
}
