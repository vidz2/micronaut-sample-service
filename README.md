## Sample Service using Micronaut

---
This repository contains a simple user register service built using Micronaut having a couple of endpoints.

### Pre-requisites
- Java 11
- Maven 3.x
- Docker ( if running the app in a container )

You need to be in the root directory for all the following commands.

### Build
To build and run all tests, run the following command from terminal:
```
./build_and_test.sh 
```

To build without running tests, run the following command:
```
./build_only.sh 
```

### Running the application locally
Run the following command to start the application:
```
./mvnw mn:run
```
Application should be started and running at http://localhost:8080 by default. You can change the server host and/or port by passing these properties:
```
./mvnw mn:run -Dmn.appArgs="-micronaut.server.port=3500"
```

### Running the application in docker container
Run the following command to build the docker image. 
```
./build_docker.sh
```

The following command would build the image and start the container. The script checks if the image exists locally and builds only if it does not exist.
```
./docker-build-run.sh
```

### User Register Endpoints
The following endpoints are exposed currently:

#### POST: /user
This endpoint creates a user in the database. Make a POST request to the endpoint as below:
```
curl -v http://localhost:8080/user -H 'content-type: application/json'  --data-binary '{"lastName": "TheCat","firstName": "Alfie", "email": "alfie@test.com" }'

```
Successful creation should result in 201 CREATED response.

#### GET: /user/{lastname}
This endpoint gets the user(s) with the given lastName. A non-existing last name would result in a 404 Not Found error.

Make a GET request to the endpoint as below:
```
curl -v http://localhost:8080/user/TheCat
```
