# Banking System - Customer Microservice
This microservice is part of the project the NTT Data Tech Girls program. It provides a CRUD API for managing customers in the banking system. Developed with Spring Boot, it incorporates functional programming practices and is integrated with a MySQL database. OpenAPI documentation is included, and the API can be tested with Postman. Unit tests are included and code coverage can be tested with JaCoCo, as well as, code style validation with Checkstyle.

## Table of Contents
- [Technologies and Approaches](#technologies-and-approaches)
- [UML Diagrams](#uml-diagrams)
- [Postman](#postman)
- [Swagger / OpenAPI Documentation](#swagger--openapi-documentation)
- [Code Quality and Coverage](#code-quality-and-coverage)

---

## Technologies and Approaches

The microservice was built using the following technologies and development practices:

- **Spring Boot**: Framework for building and deploying the microservice.
- **Functional Programming**: Applied to validation and processing logic for a concise, stateless, and more predictable codebase.
- **MySQL Database**: Used to persist customer data. The schema is structured to maintain unique records for each customer, with validations implemented for critical fields.
- **OpenAPI (Swagger)**: Provides API documentation and allows for easy exploration of the endpoints.
- **Postman**: Facilitates testing and interaction with the microservice endpoints.
- **JUnit-Mockito**: For unit testing the main classes of the service.
- **JaCoCo-Checkstyle**: To verify code covergae and best practices in code style.

## UML Diagrams

The following UML diagrams illustrate the architecture and data flow of the Customer Microservice:

1. **Sequence Diagram**: Details the typical flow of operations between the Customer and [Account microservices](https://github.com/abengl/NTT-Project2-AccountMS).
  <img alt="UML sequence diagram" src="https://github.com/abengl/NTT-Project2-CustomerMS/blob/0a060c1b280532fa913375c508347e6960ba6823/src/main/resources/static/UML_Sequence_Diagram1_Microservices.png" width="500" height="500">
2. <b>Component Diagram</b>: Show the overall architecture of the microservices.
    <img alt="UML sequence diagram" src="https://github.com/abengl/NTT-Project2-CustomerMS/blob/0a060c1b280532fa913375c508347e6960ba6823/src/main/resources/static/UML_Component_Diagram_Microservices.png" width="800" height="400">

## Postman

1. **Import the Collection**: Download or clone the repository, then import the Postman collection file located in the `/postman` directory.
2. **Import Environment Variables**: import the environment variables into Postman and set them to run with the test collection.
3. **Run Tests**: Once configured, you can execute requests to test each endpoint. The collection provides requests for creating, retrieving, updating, and deleting customer records.

## Swagger / OpenAPI Documentation

The Customer Microservice includes integrated OpenAPI documentation. You can access it via Swagger UI by navigating to the following endpoint once the service is running:

- **Swagger UI**: [http://localhost:8085/v1/swagger-ui.html](http://localhost:8085/v1/swagger-ui.html)

Through the Swagger UI, you can interact with each endpoint, review input and output specifications, and validate response codes and messages.

## Code Quality and Coverage

To maintain code quality and ensure adequate test coverage, the project uses **Checkstyle** for code analysis and **JaCoCo** for test coverage reports. Follow the steps below to run these tools:

### Run Checkstyle
1. Open a terminal and navigate to the project directory.
2. Run the following command to perform a Checkstyle analysis:
   ```bash
   mvn checkstyle:check
   ```
3. Review the output in the terminal for any code style violations. The results will also be saved in the target/reports/checkstyle.html file.
   
### Run JaCoCo for Test Coverage
1. In the terminal, run the tests with coverage analysis:
  ```bash
  mvn clean test
  ```
2. Open the generated report located at target/site/jacoco/index.html in your browser to review coverage details.
