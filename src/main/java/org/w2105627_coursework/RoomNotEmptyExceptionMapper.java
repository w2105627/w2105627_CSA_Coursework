package org.w2105627_coursework;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException exception) {
        ErrorMessage errorMessage = new ErrorMessage("Resource Conflict",409,
                "Room is currently occupied by active hardware");
        return Response.status(Response.Status.CONFLICT).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
    }
}
