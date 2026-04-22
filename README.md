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
