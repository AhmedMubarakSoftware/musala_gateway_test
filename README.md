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

Database: MySQL

Automated build: Apache Maven

UI:  Angular

Database Script
--------------------------------------------------------------------
to create the database and it's tables run musala_db.sql in the MySql db editor .

to build and run from linux
--------------------------------------------------------------------

To build open a terminal and execute ./build_backend_linux.sh

To run open a terminal and execute ./run_backend_linux.sh

to build and run from windows
--------------------------------------------------------------------
To build open a console and execute build_backend.bat

To run open a console and execute run_backend.bat

Endpoints:
--------------------------------------------------------------------
--------------------------------------------------------------------
Create a gateway:
--------------------------------------------------------------------
POST http://localhost:8080/gateways
body: {
"serial": "string", //a unique serial number ex: AbC123
"name": "string", //a human-readable name ex: Gateway A
"ip": "string" //an IPv4 address ex: 10.0.0.1
}

Delete a gateway:
DELETE http://localhost:8080/gateways/{serial} // ex: http://localhost:8080/gateways/AbC123

Get all stored gateways:
GET http://localhost:8080/gateways

Get a single gateway:
GET http://localhost:8080/gateways/{serial} // ex: http://localhost:8080/gateways/AbC123

Add a device from a gateway
POST http://localhost:8080/gateways/{serial}/device
body: {
"vendor": "string", // ex: Vendor A
"status": "online|offline" // ex: online
}

Remove a device from a gateway
DELETE http://localhost:8080/gateways/{serial}/device/{device_uid} // ex: http://localhost:8080/gateways/AbC123/device/1
