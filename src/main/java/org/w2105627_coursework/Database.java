package org.w2105627_coursework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class Database {
    private static final Database db = new Database();

    private ConcurrentHashMap<String,Object> resources = new ConcurrentHashMap<>();

    private Database(){
        initDB();
    };

    public void initDB(){
        List<Room> rooms = new ArrayList<>();
        resources.put("rooms",rooms);

        List<Sensor> sensors = new ArrayList<>();
        resources.put("sensors",sensors);


        HashMap<String,List<SensorReading>> sensorReadings = new HashMap<>();
        resources.put("sensorReadings",sensorReadings);
    }

    public static Database getInstance(){
        return db;
    }

    public ConcurrentHashMap<String,Object> getResources(){
        return resources;
    }


}
