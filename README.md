# musala_gateway_test
Description 
--------------------------------------------------------------------
a project for managing gateways - master devices that control multiple peripheral devices .

Consist from 
--------------------------------------------------------------------
an angular + REST service (JSON/HTTP) for storing information about gateways and their associated devices.

Technologies and framworks Used :
--------------------------------------------------------------------
Programming language: Java

Framework: Spring Boot

Additional tools and Libs : Swagger tools , Lombok Lib

Database: MySQL

Automated build: Apache Maven

UI:  Angular ( Not complete)

Steps for working 
--------------------------------------------------------------------
#1.DataBase Creation 
--------------------------------------------------------------------
1.1. create the database and it's tables run the commands in the /files/musala_db.sql in the MySql db editor .

#2.Building and Running
--------------------------------------------------------------------
2.1. build and run from linux

 - build open a terminal and execute ./build_backend_linux.sh

 - run open a terminal and execute ./run_backend_linux.sh

2.2. build and run from windows

- build open a console and execute build_backend.bat

- run open a console and execute run_backend.bat

#3. Viewing and interacting with All EndPoints 
--------------------------------------------------------------------
- open the browser and type http://localhost:8081/swagger-ui.html#/

- navigate throw all Endpoints in the gatewayController like :

Get all stored gateways:
--------------------------------------------------------------------
GET http://localhost:8081/api/v1/gateways

Create a gateway:
--------------------------------------------------------------------
POST http://localhost:8081/api/v1/gateways

body: {

"serialNum": "string", //a unique serial number ex: AbC123

"name": "string", //a human-readable name ex: Gateway A

"ipAddress": "string" //an IPv4 address ex: 10.0.0.1
"peripheralDevices": [{"vendor": "ahmed","status": "online"}] // optional list of devices (can be null )
}

update a gateway by it's id with another gateway
--------------------------------------------------------------------
Put http://localhost:8081/api/v1/gateways/{id}
// ex: http://localhost:8081/api/v1/gateways/AbC123
//and body: {

"serialNum": "string", //a unique serial number ex: AbC123

"name": "string", //a human-readable name ex: Gateway A

"ipAddress": "string" //an IPv4 address ex: 10.0.0.1
"peripheralDevices": [{"vendor": "ahmed","status": "online"}] // optional list of devices (can be null )
}


Delete a gateway:
--------------------------------------------------------------------
DELETE http://localhost:8081/api/v1/gateways/{serial}
// ex: http://localhost:8081/api/v1/gateways/AbC123

Get a single gateway:
--------------------------------------------------------------------
GET http://localhost:8081/api/v1/gateways/{serial} 

// ex: http://localhost:8081/api/v1/gateways/AbC123

Add a device from a gateway
--------------------------------------------------------------------

POST http://localhost:8081/api/v1/gateways/{serial}/addDevice

body: {

"vendor": "string", // ex: Vendor A

"status": "online|offline" // ex: online

}

Remove a device from a gateway
--------------------------------------------------------------------

DELETE http://localhost:8081/api/v1/gateways/{serial}/deleteDevice/{uid} 

// ex: http://localhost:8081/api/v1/gateways/AbC123/deleteDevice/1
