# Gateways
## Description
This sample project is managing gateways - master devices that control multiple peripheral
devices.

Your task is to create a REST service (JSON/HTTP) for storing information about these
gateways and their associated devices. This information must be stored in the database.
When storing a gateway, any field marked as “to be validated” must be validated and an
error returned if it is invalid. Also, no more that 10 peripheral devices are allowed for a
gateway.

The service must also offer an operation for displaying information about all stored gateways
(and their devices) and an operation for displaying details for a single gateway. Finally, it
must be possible to add and remove a device from a gateway.

### Each gateway has:
- a unique serial number (string)
- human-readable name (string),
- IPv4 address (to be validated),
- multiple associated peripheral devices.

### Each peripheral device has:
- a UID (number),
- vendor (string),
- date created,
- status - online/offline.

## Running the application

Install docker-compose 
```
$> sudo apt install docker-compose
```

Add current user to docker group if it is not added

```
$> sudo usermod -aG docker $USER
```

Create a file with name ".env" inside the project directory and define needed environment variables
```
GW_MYSQL_PORT=3306
GW_MYSQL_DATABASE=gateways
GW_MYSQL_USER=user
GW_MYSQL_PASSWORD=pass****
GW_BACKEND_PORT=8080
GW_APP_STORAGE=./gw-storage
GW_MAVEN_M2_DIR=~/.m2
```

Run docker-compose
````
$> docker-compose up
````

Open Swagger UI on a web browser to test the API
```
http://<host>:<port>/swagger-ui.html
```

