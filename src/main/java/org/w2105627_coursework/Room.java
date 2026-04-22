package org.w2105627_coursework;

import java.util.ArrayList;
import java.util.List;


public class Room{
    private String id; // Unique identifier, e.g., "LIB-301"
    private String name; // Human-readable name, e.g., "Library Quiet Study"
    private int capacity; // Maximum occupancy for safety regulations
    private List<String> sensorIDs = new ArrayList<>(); // Collection of IDs of sensors deployed in this room
    // Constructors, getters, setters...

    public Room(){};
    public Room(String id_){this.id = id_;}
    public Room(String id, String name, int capacity, List<String> sensorIDs){
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.sensorIDs = sensorIDs;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
    public void setSensorIds(List<String> sensorIDs){
        this.sensorIDs = sensorIDs;
    }
    public void appendSensor(String sensorId){
        sensorIDs.add(sensorId);
    }
    public String getId(){return this.id;}
    public String getName(){return this.name;}
    public int getCapacity(){return this.capacity;}
    public List<String> getSensorIDs(){return this.sensorIDs;}


}




