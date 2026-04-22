package org.w2105627_coursework;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/sensors")
public class SensorResource {

    private Database db = Database.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensors(@QueryParam("type") String type){
        List<Sensor> sensors = (List<Sensor>) db.getResources().get("sensors");
        if(type == null) {
            return Response.ok(sensors).build();
        }
        List<Sensor> filtered = new ArrayList<>();
        for(Sensor sensor : sensors){
            if(sensor.getType().equalsIgnoreCase(type)){
                filtered.add(sensor);
            }
        }

        return Response.ok(filtered).build();
    }



    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSensor(Sensor sensor){

        if(sensor == null ||
        sensor.getId() == null ||
        sensor.getType() == null||
        sensor.getRoomId() == null || sensor.getStatus() == null)
        {
         throw new CatchAlLException("A value passed was NULL");
        }

        String roomId = sensor.getRoomId();

        List<Room> rooms = (List<Room>) db.getResources().get("rooms");
        Room targetRoom = null;

        for(Room room : rooms){
            if(room.getId().equalsIgnoreCase(roomId)){
                targetRoom = room;
            }
        }
        if(targetRoom == null){
          throw new LinkedResourceNotFoundException("Unprocessable entity, passed roomID is not valid.");
        }


        List<Sensor> sensors = (List<Sensor>)db.getResources().get("sensors");
        for(Sensor s : sensors){
            if(s.getId().equalsIgnoreCase(sensor.getId())){
                return Response.status(Response.Status.CONFLICT).build();
            }
        }

        targetRoom.appendSensor(sensor.getId());


        sensors.add(sensor);
        return Response.status(Response.Status.CREATED).build();
    }



    @Path("/{id}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("id")String id){
        return new SensorReadingResource(id);
    }
}
