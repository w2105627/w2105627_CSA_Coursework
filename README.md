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

To build and deploy the project:
- Open the project in a IDE
- Configure a Apache TOMCAT server
- Deploy the project on TOMCAT through the NetBeans built in plugin, or the external SmartTomcat plugin for Intellij.
- Launch the server
Once launched, the api can be accessed through http://localhost:8080/smartcampus/api/v1.

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
In JAX-RS, by default a resource class is instantiated on a per request basis, rather than it acting as a singleton.
This matches the expected stateless architecture of REST, where request-specific data handling is separated from a persistent application state. 
Due to this, I explicitly do not store any data that I want to persist past the lifetime of the instance of that request resource, within the data members of a resource class, since these go out of scope at the end of each request, and any data is lost. Instead, any data that I want to modify the application state is appended to a data structure that is created once upon startup and then persists across the lifetime of the app, which is the singleton approach I took for data storage.
Strategies for synchronizing in memory data and preventing race conditions would include using thread safe data structures, such as ConcurrentHashMap, and locking data once you access it, to prevent other threads from updating it while you are still modifying it, leading to that thread modifying stale data.


- Question: Why is the provision of Hypermedia (links and navigation within responses)
  considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach
  benefit client developers compared to static documentation?

Hypermedia is considered a hallmark of RESTful design because it allows for dynamic interactions between the client and server. Embedding navigation paths within a response allows for the API to be self-documenting, and lets the client figure out how to navigate through the application, which is something that cannot be done with static documentation.
Developers get the benefit of not needing to hardcode every link inside the application, which can speed up development time, as clients are now made to follow a path provided by a response, rather than hardcoding URLs within the application, which makes for harder iteration over development, as it requires more code to be changed each time you want to implement something new.


Question: When returning a list of rooms, what are the implications of returning only
IDs versus returning the full room objects? Consider network bandwidth and client side
processing.


Choosing to return only room IDs will have the benefit of reducing the response size and thus the bandwidth used for the response, which can free up a notable amount of resources if the resource being returned is particularly large. However, it has the downside of not giving the client the full picture of what it might want, which leads to the client needing to do more processing and making additional requests if the data is missing.
Because of this, returning full objects has the clear convenience of covering all bases, and the client has access to all possible information it might want. However, in the case where the client doesn't need all the information, this can be considered a clear waste of resources.


Question: Is the DELETE operation idempotent in your implementation? Provide a detailed
justification by describing what happens if a client mistakenly sends the exact same DELETE
request for a room multiple times.

Yes, a DELETE operation is idempotent in my implementation. Upon a successful delete, the resource is removed, and any subsequent identical requests will return a 404 Not Found, and the final server state will remain the same after the first operation. Upon a successful deletion, 204 No Content is returned. Upon an attempt to delete a room with active sensors, a 409-resource conflict is returned with an error message indicating why it cannot be removed. If there is no room with the given id, then a 404 is returned.

Question: We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on
the POST method. Explain the technical consequences if a client attempts to send data in
a different format, such as text/plain or application/xml. How does JAX-RS handle this
mismatch?

@Consumes tells JAX-RS to only accept a properly formatted JSON request body. If an attempt is made to send another content type, such as XML or plain text, JAX-RS will reject it and return a 415 Unsupported Media type. This happens prior to my business logic running, which protects my runtime storage from being corrupted with malformed data. If the content is a malformed JSON body, then it will also reject it before attempting to pass it to the application.

Question: You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/vl/sensors/type/CO2). Why
is the query parameter approach generally considered superior for filtering and searching
collections?

Question: Discuss the architectural benefits of the Sub-Resource Locator pattern. How
does delegating logic to separate classes help manage complexity in large APIs compared
to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?


The Sub-Resource locator pattern benefits the architecture of your application by redirecting nested resource code to a dedicated class. SensorResource handles the managing of specific sensor instances, whereas a sensor reading is an entirely new resource, which is handled by its own class. This separation allows for smaller classes, which promote readability, scalability and debugging. It also allows your code to mimic the actual path of the resource, rather than having a single class which contains code for a multitude of paths.
Usage of @QueryParam is generally considered a more optimal approach when compared against PathParam due to a few key reasons. One, it makes it clear that you are still requesting the same resource, and just applying a filter to it, rather than adding another layer to the URL path, which indicates that you are requesting an entirely new resource. On top of this, Query parameters can easily be scaled, and can be passed in any order, whereas path parameters do not have this benefit.


Question: Why is HTTP 422 often considered more semantically accurate than a standard
404 when the issue is a missing reference inside a valid JSON payload?

Returning a 404 response would indicate that the resource requested does not exist at all on the server, which would be an incorrect response in this case, due to the fact that the resource does exist, but the request body has a problem. Due to this, returning a 422 unprocessable entity makes it clear that there is no problem with the endpoint requested, but rather a semantic problem with the request payload. It cleraly indicates that the request is being rejected by the business logic of the code, rather than the server.


Question: From a cybersecurity standpoint, explain the risks associated with exposing
internal Java stack traces to external API consumers. What specific information could an
attacker gather from such a trace?

Exposing a stack trace is unacceptable for several reasons. It can give an outsider information on the inner workings of the software, which they can use maliciously.
A stack trace can include information such as class and function names, information on the versions of any frameworks being used, which could then be exploited if they have known vulnerabilities, as well as information on the file paths and directory structure. It can also indicate the logical flow of the program and expose internal endpoints that were created only for development. All of this ultimately can give a threat actor a new attack vector against the program, as they can identify more weak points and form extremely well targeted attacks.


Question: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like
logging, rather than manually inserting Logger.info() statements inside every single resource method?

If you were to take the manual approach, it would lead to enormous amounts of code duplication, and inevitably lead to forgetting to implement it when creating new methods. JAX-RS filters bypasses this by ensuring it will always apply to a new part of the code, by centralising the logging logic into one class. It allows you to update logging logic in one place, as if it was manually placed in every class, you would need to iterate through each one to make even minor changes. It is also incorrect from a programming standpoint to mix cross-cutting concerns such as debugging info with the real business logic of the program.
