# Pharmacy Management System (REST API)

A layered, object-oriented RESTful API built with **Spring Boot 3** and **Java 21** for managing pharmacy operations. This application handles patients, doctors, treatments, prescriptions, and drug inventory validation, complying with complex business rules.

It features secure access via API Keys, integration with the **OpenFDA API** for drug verification, and comprehensive API documentation using **Swagger/OpenAPI**.

---

## Authors: 
Popa Marian-Iulian, Pirvu George-Cristian, Man Alexandra  
Grupa 10LF332

## Key Features

### 1. **Core Business Operations**
- **Person Management**: Manage Patients, Doctors, and Pharmacists with role-based validation.
- **Drug & Inventory**: centralized drug database with inventory tracking per pharmacy location.
- **Prescriptions**: Link patients, doctors, and treatments. Automated validation ensures prescribed drugs exist in inventory.
- **Purchases**: Record transactions, validate stock availability, and calculate totals.
- **Treatments**: Manage long-term treatment plans and prevent overlapping conflicts.

### 2. **Technical Highlights**
- **External API Integration**: Real-time drug validation using the **OpenFDA API** (`api.fda.gov`).
- **Security**: Custom **API Key Authentication** filter securing all endpoints.
- **Documentation**: Interactive API documentation available via **Swagger UI**.
- **Data Mapping**: Efficient DTO-to-Entity mapping using **MapStruct**.
- **Database**: Relational design with **PostgreSQL**, utilizing stored procedures for complex logic.

---

## Technology Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.5.7
- **Database:** PostgreSQL 21
- **ORM:** Spring Data JPA (Hibernate)
- **Security:** Spring Security (Custom API Key Filter)
- **Utilities:** Lombok, MapStruct
- **Testing:** JUnit 5, Mockito
- **API Documentation:** SpringDoc OpenAPI (Swagger UI)

---

## Architecture

The project follows a **Multi-Layered Architecture** designed to separate concerns, improve maintainability, and isolate business rules.

### 1. Presentation Layer (`Controllers`)
- **Role**: Handles incoming HTTP requests and creates HTTP responses.
- **Responsibility**: Validates input formats, invokes the Service layer, and returns DTOs to the client.
- **Path**: `com.mpp.pharmacy.Controllers`

### 2. Service Layer (`Services`)
- **Role**: Acts as the transaction boundary and orchestrator.
- **Responsibility**: 
  - Handles DTO $\leftrightarrow$ Entity conversion using Mappers.
  - Manages database transactions (`@Transactional`).
  - Calls the Domain layer for core logic.
- **Path**: `com.mpp.pharmacy.Services`

### 3. Domain Layer (`Domain`)
- **Role**: Encapsulates the core business logic and rules.
- **Responsibility**: 
  - Validates business rules (e.g., checking for overlapping treatments).
  - Orchestrates calls to `Validators` and `Repositories`.
  - Ensures data integrity before persistence.
- **Path**: `com.mpp.pharmacy.Domain`

### 4. Validation Layer (`Validators`)
- **Role**: specialized logic for validating entities.
- **Responsibility**: Enforces specific constraints (e.g., "End date cannot be before Start date", "Doctor role required").
- **Path**: `com.mpp.pharmacy.Validators`

### 5. Mapping Layer (`Mapper`)
- **Role**: Object-to-Object mapping.
- **Responsibility**: Uses **MapStruct** to cleanly convert between internal Entities and public DTOs, decoupling the database schema from the API contract.
- **Path**: `com.mpp.pharmacy.Mapper`

### 6. Data Access Layer (`Repository`)
- **Role**: Database interaction.
- **Responsibility**: Extends `JpaRepository` to perform CRUD operations and execute custom SQL queries.
- **Path**: `com.mpp.pharmacy.Repository`

### 7. External Layer (`External`)
- **Role**: Third-party integration.
- **Responsibility**: Consumes external services like the **OpenFDA API** to verify drug data.
- **Path**: `com.mpp.pharmacy.External`

---

## Setup & Configuration

### 1. Prerequisites
- Java 21 SDK
- Maven
- PostgreSQL Database

### 2. Environment Variables
The application requires the following environment variables to be set (or defined in a `.env` file):

| Variable | Description |
| :--- | :--- |
| `DATABASE_URL` | JDBC URL (e.g., `jdbc:postgresql://localhost:5432/database_name`) |
| `DATABASE_USERNAME` | PostgreSQL username |
| `DATABASE_PASSWORD` | PostgreSQL password |
| `fda-api-key` | Your OpenFDA API Key |
| `api-key-name` | Name of the header for auth (e.g., `X-API-KEY`) |
| `api-key-value` | The secret key value required to access the API |

### 3. Installation
```bash
# Clone the repository
git clone <repository-url>

# Navigate to project directory
cd Pharmacy-Management

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run

```

### 4. Database Initialization
Execute the `db-init.sql` and `stored-procedures.sql` scripts in your PostgreSQL instance to create the schema and seed initial data.

## API Documentation
Once the application is running, you can access the interactive API documentation and test endpoints directly:

- Swagger UI: http://localhost:8080/swagger-ui.html

- OpenAPI Docs: http://localhost:8080/v3/api-docs

    **Note:** You must authorize requests in Swagger by clicking the "Authorize" button and entering your configured api-key-value.

## Testing

The project includes over 100 unit tests covering:

- Service layer business logic.

- Controller endpoints (MockMvc).

- External API mocking (OpenFDA).

Run tests using Maven:

``` bash
mvn test 

# for a clean build use
mvn clean test 

# also if you want more information use the `-X` argument
mvn test -X
```

## Database Schema
The database supports the following relationships:

- **Inventory** links **Pharmacies** and **Drugs**.

- **Prescriptions** link **Patients**, **Doctors**, and **Treatments**.

- **Purchases** track sales history for reporting.

<img width="1588" height="1818" alt="Database Schema" src="https://github.com/user-attachments/assets/6f82829a-2f47-4942-9f8c-4a29a718a2a8" />
