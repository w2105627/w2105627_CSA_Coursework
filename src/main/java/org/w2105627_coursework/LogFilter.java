package org.w2105627_coursework;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Provider
public class LogFilter implements ContainerRequestFilter, ContainerResponseFilter {

    static final Logger logger = Logger.getLogger(LogFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        logger.info("[NEW REQUEST]");
        logger.info("Request type: " + requestContext.getMethod() +
                " ,request URI: " + requestContext.getUriInfo().getAbsolutePath());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        logger.info("[OUTGOING RESPONSE]");
        logger.info("Status : " + responseContext.getStatus());
    }
}
