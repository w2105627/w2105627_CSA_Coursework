package org.w2105627_coursework;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;
import java.util.Map;




@Path("/")
public class DiscoveryResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApi() {
       Map<String,Object> response = new LinkedHashMap<>();

       response.put("api name", "W2105627 Smart Campus Sensor");
       response.put("version","v1");
       response.put("path","/api/v1");

       Map<String,String> adminResp = new LinkedHashMap<>();
       adminResp.put("Name","AOB");
       adminResp.put("email","w2105627@westminster.ac.uk");
       adminResp.put("role","admin");

       Map<String,String> endpoints = new LinkedHashMap<>();
       endpoints.put("rooms","/api/v1/rooms");
       endpoints.put("room search","/api/v1/rooms/{roomid}");
       endpoints.put("sensors","/api/v1/sensors");
       endpoints.put("sensor search","/api/v1/sensors/{sensorid}");
       endpoints.put("sensor readings","/api/v1/sensors/{sensorid}/readings");

       response.put("Server Admin Details",adminResp);
       response.put("API Endpoints",endpoints);
       return Response.ok(response).build();
    }
}
