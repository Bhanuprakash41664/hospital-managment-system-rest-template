# Patient Service

Part of the Hospital Management System. Port **8081**, database **patient_db** (MySQL).

## Tech stack
Java 17, Spring Boot 3.3, Spring Data JPA, MySQL, Flyway, Bean Validation, Lombok,
springdoc OpenAPI (Swagger), Actuator, JUnit 5 + Mockito.

## Layers
    controller -> HTTP only (request/response, status codes)
    service    -> business rules, transactions
    repository -> database access
    dto        -> API input/output models
    mapper     -> entity <-> DTO conversion
    entity     -> database model
    exception  -> custom exceptions + global error handler

## Run
    # needs Java 17, Maven and a running MySQL
    mvn spring-boot:run

Defaults (profile `dev`): MySQL on localhost:3306, user `root`, password `root`.
Override with environment variables `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.
Production: `SPRING_PROFILES_ACTIVE=prod` and set all three variables.

## Test
    mvn test

## Links
- Swagger UI: http://localhost:8081/swagger-ui.html
- Health:     http://localhost:8081/actuator/health

## API (base path /api/v1/patients)
| Method | URL                          | Description                          | Success |
|--------|------------------------------|--------------------------------------|---------|
| POST   | /api/v1/patients             | Create a patient                     | 201     |
| GET    | /api/v1/patients/{id}        | Get a patient by id                  | 200     |
| GET    | /api/v1/patients             | List patients (page, size, sort)     | 200     |
| PUT    | /api/v1/patients/{id}        | Update a patient                     | 200     |
| DELETE | /api/v1/patients/{id}        | Delete a patient                     | 204     |

Errors: 400 validation/bad JSON, 404 not found, 409 duplicate email, 500 unexpected.

## Examples
    curl -X POST localhost:8081/api/v1/patients -H "Content-Type: application/json" \
      -d '{"name":"Bhanu","email":"bhanu@gmail.com","dateOfBirth":"2002-05-10","address":"Hyderabad"}'

    curl "localhost:8081/api/v1/patients?page=0&size=10&sort=name,asc"
