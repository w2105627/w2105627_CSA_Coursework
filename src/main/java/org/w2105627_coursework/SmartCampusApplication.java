package org.w2105627_coursework;


import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.*;

// use package scanning instead?
@ApplicationPath("/api/v1")
public class SmartCampusApplication extends Application {
    @Override
    public Set<Class<?>> getClasses(){


        Set<Class<?>> classes = new HashSet<>();

        classes.add(DiscoveryResource.class);

        classes.add(SensorRoomResource.class);
        classes.add(SensorResource.class);

        classes.add(RoomNotEmptyExceptionMapper.class);
        classes.add(LinkedResourceNotFoundExceptionMapper.class);
        classes.add(SensorUnavailableExceptionMapper.class);
        classes.add(CatchAllExceptionMapper.class);
        classes.add(LogFilter.class);
        return classes;
    }
}