# Banking System - Customer Microservice
This microservice is part of the second project deliverable for the NTT Data Tech Girls program. It provides a CRUD API for managing customer banking management. Developed with Spring Boot, it incorporates functional programming practices and is integrated with a MySQL database. OpenAPI documentation is included, and the API can be tested with Postman.

## Table of Contents
1. [Project Description](#project-description)
2. [Technologies and Approaches](#technologies-and-approaches)
3. [UML Diagrams](#uml-diagrams)
4. [Running the Postman Collection](#running-the-postman-collection)
5. [Swagger / OpenAPI Documentation](#swagger--openapi-documentation)

---

## Project Description

The **Customer Microservice** handles operations related to customer banking management, offering a RESTful API that supports basic CRUD operations. The microservice operates independently and connects to a MySQL database to manage customer data. This service is designed for use within a microservices architecture, ensuring modularity and separation of concerns between different banking functionalities (e.g., customer registration, data update, registration removal, etc.).

## Technologies and Approaches

The microservice was built using the following technologies and development practices:

- **Spring Boot**: Framework for building and deploying the microservice.
- **Functional Programming**: Applied to validation and processing logic for a concise, stateless, and more predictable codebase.
- **MySQL Database**: Used to persist customer data. The schema is structured to maintain unique records for each customer, with validations implemented for critical fields.
- **OpenAPI (Swagger)**: Provides API documentation and allows for easy exploration of the endpoints.
- **Postman**: Facilitates testing and interaction with the microservice endpoints.

## UML Diagrams

The following UML diagrams illustrate the architecture and data flow of the Customer Microservice:

1. **Sequence Diagram**: Details the typical flow of operations between the Customer and [Account microservices](https://github.com/abengl/NTT-Project2-AccountMS).
  <img alt="UML sequence diagram" src="https://github.com/abengl/NTT-Project2-CustomerMS/blob/0a060c1b280532fa913375c508347e6960ba6823/src/main/resources/static/UML_Sequence_Diagram1_Microservices.png" width="500" height="500">
2. **Component Diagram**: Show the overall architecture of the microservices.
    <img alt="UML sequence diagram" src="https://github.com/abengl/NTT-Project2-CustomerMS/blob/0a060c1b280532fa913375c508347e6960ba6823/src/main/resources/static/UML_Component_Diagram_Microservices.png" width="800" height="500">

## Running the Postman Collection

1. **Import the Collection**: Download or clone the repository, then import the Postman collection file located in the `/postman` directory.
2. **Configure Environment Variables**: Ensure that the Postman environment variables for your MySQL database connection (if applicable) and service URL are set correctly.
3. **Run Tests**: Once configured, you can execute requests to test each endpoint. The collection provides requests for creating, retrieving, updating, and deleting customer records.

## Swagger / OpenAPI Documentation

The Customer Microservice includes integrated OpenAPI documentation. You can access it via Swagger UI by navigating to the following endpoint once the service is running:

- **Swagger UI**: [http://localhost:8085/swagger-ui.html](http://localhost:8085/swagger-ui.html)

Through the Swagger UI, you can interact with each endpoint, review input and output specifications, and validate response codes and messages.

