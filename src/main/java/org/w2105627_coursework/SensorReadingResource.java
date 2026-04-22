package org.w2105627_coursework;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SensorReadingResource {

    private Database db = Database.getInstance();
     String sensorId;

    public SensorReadingResource(){}
    public SensorReadingResource(String id){
        this.sensorId = id;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings(){
        HashMap<String,List<SensorReading>> readingsMap = (HashMap<String,List<SensorReading>>) db.getResources().get("sensorReadings");
        List<SensorReading> readings = readingsMap.get(this.sensorId);
        if(readings == null){
            readings = new ArrayList<>();
        }
        return Response.ok(readings).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postReading(SensorReading reading){

        if(reading == null || reading.getId() == null){
            throw new CatchAlLException("A value passed was NULL");
        }

        List<Sensor> sensors = (List<Sensor>) db.getResources().get("sensors");
        Sensor sensor = null;
        for(Sensor sensor_ : sensors){
            if(sensor_.getId().equalsIgnoreCase(sensorId)){
                sensor = sensor_;
            }
        }

        if(sensor == null){
            throw new LinkedResourceNotFoundException("Sensor doesnt exist");
        }

        if(sensor.getStatus().equalsIgnoreCase("MAINTENANCE")){
            throw new SensorUnavailableException("Sensor is down for maintenance, try again later.");
        }

        HashMap<String,List<SensorReading>> readingsMap = (HashMap<String,List<SensorReading>>) db.getResources().get("sensorReadings");
        List<SensorReading> readings = readingsMap.get(this.sensorId);
        if(readings == null){
            readings = new ArrayList<>();
            readingsMap.put(sensorId,readings);
        }


        sensor.setCurrentValue(reading.getValue());


        readings.add(reading);
        return Response.status(Response.Status.CREATED).build();
    }
}