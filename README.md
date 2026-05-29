# Smart Parking Reservation System

A Smart Parking Reservation System built using Spring Boot and MySQL. 
This application provides REST APIs to manage parking slots, allowing users to reserve and release slots.

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- Docker & Docker Compose

## Prerequisites

- Java 21
- Maven
- Docker and Docker Compose (if running via Docker)

## Project Overview

The system manages parking slots (default 20 created on startup). It is backed by a MySQL database (`parking_db`). The application provides endpoints to view all slots, reserve a specific slot, and release a reserved slot.
The project is structured following clean architecture principles, with distinct packages for controllers, services, repositories, entities, exceptions, and configurations.

## How to run locally (Without Docker)

1. Make sure you have a local MySQL instance running on port 3306 with the root password set to `root` and a database named `parking_db` created (or the app will create the schema automatically).
2. Build and run using Maven:
```bash
./mvnw spring-boot:run
```

## How to run using Docker Compose

1. Build the project first to generate the `.jar` file:
```bash
./mvnw clean package -DskipTests
```

2. Start the services using Docker Compose:
```bash
docker-compose up --build -d
```
The application will wait for the MySQL container to be healthy before starting.

## API Examples

### 1. View all slots
```bash
curl -X GET http://localhost:8080/slots
```

### 2. Reserve a slot (e.g., slot ID 1)
```bash
curl -X POST http://localhost:8080/slots/1/reserve
```

### 3. Release a slot (e.g., slot ID 1)
```bash
curl -X POST http://localhost:8080/slots/1/release
```

### 4. Health Check
```bash
curl -X GET http://localhost:8080/health
```
test
