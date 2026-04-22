package org.w2105627_coursework;


import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/rooms")
public class SensorRoomResource {
    private Database db = Database.getInstance();
    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRooms(){
        return Response.ok(db.getResources().get("rooms")).build();
    }



    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomByID(@PathParam("id")String id){
        List<Room> rooms = (List<Room>) db.getResources().get("rooms");
        for(Room room : rooms){
            if(room.getId().equalsIgnoreCase(id)){
                return Response.ok(room).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage("Not Found", 404, "Room not found"))
                .build();
    }



    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoom(Room room){

        if(room == null || room.getId() == null || room.getName() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Room> rooms = (List<Room>) db.getResources().get("rooms");
        for(var r : rooms){
            if(r.getId() != null && r.getId().equalsIgnoreCase(room.getId())){
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorMessage("Conflict", 409, "Sensor already exists"))
                        .build();
            }
        }
        rooms.add(room);

        URI LocationHeader = uriInfo.getAbsolutePathBuilder().path(room.getId()).build();

        return Response.created(LocationHeader).entity(room).build();

    }


    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("id") String id){

        if(id == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<Sensor> sensors = (List<Sensor>) db.getResources().get("sensors");
        for(Sensor sensor : sensors){
            if(id.equalsIgnoreCase(sensor.getRoomId())){
                throw new RoomNotEmptyException("Room : " + id + " has active sensors and cannot be deleted.");
            }
        }

        List<Room> rooms = (List<Room>) db.getResources().get("rooms");
        for(int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            if (room.getId().equalsIgnoreCase(id)) {
                rooms.remove(i);
                return Response.status(Response.Status.NO_CONTENT).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage("Not Found", 404, "Room not found"))
                .build();
        }
    }

