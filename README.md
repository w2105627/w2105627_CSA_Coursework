Student ID: w2105627
This is my implementation for the 5COSC022W.2 Client-Server Architectures module at the University of Westminster. 
It is a RESTful API for a Smart Campus Sensor system. There are three main resources involved, the Room, the Sensors, and the sensor readings.
The rooms have sensors assigned to them, and each individual sensor has a history of sensor readings.

## Usage
The base endpoint is /api/v1.

### Rooms
To fetch all rooms, the endpoint is /api/v1/rooms. This returns representations of all rooms in the system.
To fetch a specific room, you can use /api/v1/rooms/{roomid}.

### Sensors
To fetch all sensors, the endpoint is /api/v1/sensors
To fetch a specific sensor, the endpoint is /api/v1/sensors/{sensorid}
To filter sensors by type, you can use query parameters. i.e.:
/api/v1/sensors/sensors?type=CO2

To fetch readings for a specific sensor, you can request
/api/v1/sensors/{sensorid}/readings

The project can be run with NetBeans with Apache Tomcat or Intellij via SmartTomcat.

Example cURL commands:
1) fetch all rooms
curl http://localhost:8080/smartcampus/api/v1/rooms/
2) fetch a specific room
curl http://localhost:8080/smartcampus/api/v1/rooms/room1
3) fetch all sensors
curl http://localhost:8080/smartcampus/api/v1/sensors
4) fetch a specific sensor
curl http://localhost:8080/smartcampus/api/v1/sensors/sensor1
5) DELETE a room
curl -X "DELETE" http://localhost:8080/smartcampus/api/v1/rooms/room1

Report:

Question: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a
new instance instantiated for every incoming request, or does the runtime treat it as a
singleton? Elaborate on how this architectural decision impacts the way you manage and
synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.

Answer:
In JAX-RS, by default a Resource class is instantiated on a per-request basis, rather than it acting as a singleton.
This is in line with the stateless architecture of REST, where request specific data handling is separated from a persistent application state. 
Due to this, I explicitly do not store any data that I want to persist past the lifetime of the instance of that request resource, within the data members of a resource class, since these go out of scope at the end of each request, and any data is lost. Instead, any data that I want to modify the application state is appended to a data structure that is created once upon startup and then persists across the lifetime of the app, which is the singleton approach I took for data storage.
Strategies for synchronizing in memory data and preventing race conditions would include using thread safe data structures, such as ConcurrentHashMap, and locking data once you access it, to prevent other threads from updating it while you are still modifying it, leading to that thread modifying stale data.
