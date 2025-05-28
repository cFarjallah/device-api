# Device API

A RESTful backend service built with **Java 21** and **Spring Boot 3.5.0** for managing device resources.  
This API allows creation, retrieval, update, filtering, and deletion of device entities, with PostgreSQL as the persistent data store.

---

## 🚀 Features

-  Create, update, and delete devices
-  Filter by brand and state
-  PostgreSQL persistence using Spring Data JPA
-  Swagger UI for API documentation
-  Containerized with Docker
-  Unit tested with JUnit 5 and Mockito

---

##  Tech Stack

| Layer         | Technology              |
|---------------|--------------------------|
| Language      | Java 21                 |
| Framework     | Spring Boot 3.5.0       |
| ORM           | Spring Data JPA (Hibernate) |
| Database      | PostgreSQL              |
| Documentation | Springdoc OpenAPI / Swagger |
| Testing       | JUnit 5, Mockito        |
| Build Tool    | Maven                   |
| IDE           | IntelliJ IDEA           |
| Container     | Docker, Docker Compose  |

---

## Requirements

- Java 21+
- Maven 3.9+
- Docker (optional, for containerization)
- PostgreSQL (local or Dockerized)

---

##   Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/cFarjallah/device-api.git
cd deviceapi
```

### 2. Configure the database

####  Use your local PostgreSQL

Update `src/main/resources/application.properties`:

``` DataSource Settings
spring.datasource.url=jdbc:postgresql://localhost:5432/devices
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
```


### 3. Build the application

```
./mvnw clean package
```

### 4. Run the application

#### Locally:
```
./mvnw spring-boot:run
```

#### Or via Docker:
```
docker-compose up --build
```

---

## API Endpoints

| Method | Endpoint                         | Description                  |
|--------|----------------------------------|------------------------------|
| POST   | `/api/devices/create`            | Create a new device          |
| PUT    | `/api/devices/{id}`              | Update an existing device    |
| GET    | `/api/devices/{id}`              | Get device by ID             |
| GET    | `/api/devices/all`               | Get all devices              |
| GET    | `/api/devices/brand/{brand}`     | Filter devices by brand      |
| GET    | `/api/devices/state/{state}`     | Filter devices by state      |
| DELETE | `/api/devices/{id}`              | Delete a device              |

---

## Business Rules

- `creationTime` is set at creation and cannot be updated.
- Devices in state `IN_USE`:
- Cannot have `name` or `brand` changed.
- Cannot be deleted.

---

## API Documentation

After starting the app, access the Swagger UI at:


http://localhost:8080/swagger-ui.html


This provides full documentation and allows testing all endpoints.

---

## 🧪 Running Tests

```bash
./mvnw test
```

Includes unit tests for:
- Device creation, update, deletion

---

## Dockerization

### Dockerfile
Builds your Spring Boot app as a standalone container.

### docker-compose.yml
Starts both the app and a PostgreSQL container.

---

## Author

This project was developed as part of a coding challenge for recruitment at a Company.

---


## Future Improvements

- Add DTOs and a mapping layer
- Global error handling
- CI pipeline (e.g., GitHub Actions)

---
