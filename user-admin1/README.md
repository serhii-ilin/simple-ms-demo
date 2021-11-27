App Links
* Swagger UI http://localhost:8080/swagger-ui/
* User Controller http://localhost:8080/swagger-ui/#/user-controller
* H2 Console http://localhost:8080/h2console
* Health Check http://localhost:8080/actuator/health/

TODOs or Out of Scope:
 
* Business & Application service layers
* Transaction demarcation
* Decoupling persistence entities from Resource 
* DTO objects for request / response
* Better HTTP codes
* Better validation and exception handling
* Logging
* Spring Cloud, Discovery, Service Registry, RIBBON, distributed tracing etc
* Envelope objects for Errors and Request / Response
* Automated integration tests and/or REST (Cypress/POSTMAN/Insomnia)
* Removing Spring Data REST API. It is left just as an example
* HATEOAS
* Containerization
* Relational DB


GRPC Research
* GRPC = Introduction https://grpc.io/docs/what-is-grpc/introduction/
* GRPC = Core concepts, architecture and lifecycle https://grpc.io/docs/what-is-grpc/core-concepts/ 
* GRPC = Java Quick start : https://grpc.io/docs/languages/java/quickstart/
* GRPC = Java / Basic tutorial : https://grpc.io/docs/languages/java/basics/
* GRCP = Java / Examples https://github.com/grpc/grpc-java/tree/master/examples
* GRPC = Swapping Serialization Example - https://github.com/grpc/grpc-java/blob/master/examples/src/main/java/io/grpc/examples/advanced/HelloJsonServer.java
* GRPC / Spring Boot integration
  * gRPC-Spring-Boot-Starter
    * Github https://yidongnan.github.io/grpc-spring-boot-starter/
    * Documentation https://yidongnan.github.io/grpc-spring-boot-starter/en/
    * Works with services of Bindable interface generated out of Protobuf protocol
  * LogNet grpc-spring-boot-starter
    * https://github.com/LogNet/grpc-spring-boot-starter
* AVRO / Documentation - https://avro.apache.org/docs/1.11.0/
* AVRO = Java / Getting started https://avro.apache.org/docs/1.11.0/gettingstartedjava.html
* AVRO / GRPC package API https://avro.apache.org/docs/1.11.0/api/java/org/apache/avro/grpc/package-summary.html
* AVRO RPC https://github.com/phunt/avro-rpc-quickstart