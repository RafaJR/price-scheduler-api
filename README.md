# Price Scheduler API

REST API that calculates the applicable price for a product based on brand and application date, implementing priority-based price selection when multiple prices overlap in time.

## Table of Contents

- [Overview](#overview)
  - [Features](#features)
  - [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
  - [System Requirements](#system-requirements)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
  - [Stopping the Application](#stopping-the-application)
- [API Documentation](#api-documentation)
  - [Swagger UI](#swagger-ui)
  - [Endpoint Overview](#endpoint-overview)
  - [Example Requests](#example-requests)
- [Testing](#testing)
  - [Running Unit Tests](#running-unit-tests)
  - [Running Integration Tests](#running-integration-tests)
  - [Test Coverage](#test-coverage)
  - [Integration Test Scenarios](#integration-test-scenarios)
- [Database Access](#database-access)
  - [H2 Console](#h2-console)
  - [Test Data](#test-data)
- [Monitoring and Observability](#monitoring-and-observability)
  - [Spring Boot Actuator](#spring-boot-actuator)
  - [Available Endpoints](#available-endpoints)
- [Development Tools](#development-tools)
  - [Recommended Tools](#recommended-tools)
  - [Testing with Postman](#testing-with-postman)
- [Architecture](#architecture)
  - [Domain-Driven Design (DDD)](#domain-driven-design-ddd)
  - [Project Structure](#project-structure)
    - [Domain Layer](#domain-layer)
    - [Application Layer](#application-layer)
    - [Infrastructure Layer](#infrastructure-layer)
    - [Presentation Layer](#presentation-layer)
  - [SOLID Principles](#solid-principles)
- [Technical Details](#technical-details)
  - [Value Objects](#value-objects)
  - [Aggregate Root](#aggregate-root)
  - [Repository Pattern](#repository-pattern)
  - [Data Initialization](#data-initialization)

---

## Overview

This REST API provides a service to query the applicable price for a product given a brand identifier and an application date. When multiple prices overlap for the same product and brand, the system returns the price with the highest priority value.

### Features

- **RESTful API** with OpenAPI/Swagger documentation
- **Priority-based price selection** when multiple prices are valid
- **Domain-Driven Design (DDD)** architecture with clear layer separation
- **SOLID principles** applied throughout the codebase
- **Comprehensive validation** using Bean Validation (Jakarta Validation)
- **Global exception handling** with standardized error responses
- **H2 in-memory database** with test data pre-loaded
- **Spring Boot Actuator** for monitoring and observability
- **100% test coverage** with unit and integration tests

### Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 21 | Programming language |
| **Spring Boot** | 3.4.1 | Application framework |
| **Spring Data JPA** | 3.4.1 | Data access layer |
| **Hibernate** | 6.x | ORM implementation |
| **H2 Database** | 2.x | In-memory database |
| **SpringDoc OpenAPI** | 2.8.4 | API documentation (Swagger UI) |
| **Lombok** | 1.18.x | Boilerplate reduction |
| **Maven** | 3.9+ | Build tool |
| **JUnit 5** | 5.10+ | Testing framework |
| **Mockito** | 5.x | Mocking framework |

**Development Tools:**
- **IntelliJ IDEA** - Primary IDE
- **Git** - Version control
- **Postman** - API testing (recommended)

---

## Getting Started

### System Requirements

Before running the application, ensure you have the following installed:

| Requirement | Minimum Version | Recommended Version |
|-------------|----------------|---------------------|
| **JDK** | 21 | 21 (LTS) |
| **Maven** | 3.9.0 | 3.9.5+ |
| **Memory** | 512 MB RAM | 1 GB RAM |

**Verify Installation:**

```bash
# Check Java version
java -version
# Expected: openjdk version "21" or higher

# Check Maven version
mvn -version
# Expected: Apache Maven 3.9.0 or higher
```

### Installation

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd price-scheduler-api
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

   This will:
   - Download all dependencies
   - Compile the source code
   - Run all tests
   - Package the application as a JAR file

### Running the Application

**Option 1: Using Maven Spring Boot Plugin (Recommended for Development)**

```bash
mvn spring-boot:run
```

**Option 2: Using the JAR file (Production-like)**

```bash
# Build the JAR (if not already built)
mvn clean package -DskipTests

# Run the JAR
java -jar target/price-scheduler-api-0.0.1-SNAPSHOT.jar
```

**Expected Console Output:**

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.4.1)

...
INFO ... : Started PriceSchedulerApiApplication in 3.456 seconds
INFO ... : Total prices loaded: 4
```

The application will start on **http://localhost:8080**

### Stopping the Application

Press `Ctrl + C` in the terminal to stop the application.

---

## API Documentation

### Swagger UI

The API is fully documented using **OpenAPI 3.0** specification with interactive Swagger UI.

**Access Swagger UI:**
```
http://localhost:8080/swagger-ui/index.html
```

**OpenAPI JSON Specification:**
```
http://localhost:8080/v3/api-docs
```

From Swagger UI, you can:
- View all available endpoints
- See request/response schemas
- Test endpoints interactively
- Download the OpenAPI specification

### Endpoint Overview

#### GET /api/v1/prices

Query the applicable price for a product at a specific date and time.

**Query Parameters:**

| Parameter | Type | Required | Description | Example |
|-----------|------|----------|-------------|---------|
| `productId` | Long | Yes | Product identifier | 35455 |
| `brandId` | Integer | Yes | Brand identifier | 1 |
| `applicationDate` | LocalDateTime | Yes | Application date and time (ISO 8601) | 2020-06-14T10:00:00 |

**Response Codes:**

| Code | Description |
|------|-------------|
| 200 OK | Price found successfully |
| 400 Bad Request | Invalid parameters (validation error) |
| 404 Not Found | No applicable price found |
| 500 Internal Server Error | Unexpected server error |

**Success Response (200 OK):**

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

**Error Response (400 Bad Request):**

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "productId": "Product ID must be positive"
  },
  "timestamp": "2025-01-15T10:30:45"
}
```

### Example Requests

**Using cURL:**

```bash
# Test 1: Query at 10:00 on June 14
curl "http://localhost:8080/api/v1/prices?productId=35455&brandId=1&applicationDate=2020-06-14T10:00:00"

# Test 2: Query at 16:00 on June 14
curl "http://localhost:8080/api/v1/prices?productId=35455&brandId=1&applicationDate=2020-06-14T16:00:00"

# Test 3: Query at 21:00 on June 14
curl "http://localhost:8080/api/v1/prices?productId=35455&brandId=1&applicationDate=2020-06-14T21:00:00"

# Test 4: Query at 10:00 on June 15
curl "http://localhost:8080/api/v1/prices?productId=35455&brandId=1&applicationDate=2020-06-15T10:00:00"

# Test 5: Query at 21:00 on June 16
curl "http://localhost:8080/api/v1/prices?productId=35455&brandId=1&applicationDate=2020-06-16T21:00:00"
```

**Using Browser:**

Simply paste the URLs in your browser:
```
http://localhost:8080/api/v1/prices?productId=35455&brandId=1&applicationDate=2020-06-14T10:00:00
```

---

## Testing

### Running Unit Tests

Unit tests verify individual components in isolation using mocks.

```bash
# Run all unit tests
mvn test

# Run tests for a specific package
mvn test -Dtest="com.inditex.priceschedulerapi.domain.**"

# Run a specific test class
mvn test -Dtest=PriceSelectionServiceTest
```

### Running Integration Tests

Integration tests verify the complete flow from HTTP request to database.

```bash
# Run all tests (unit + integration)
mvn verify

# Run only integration tests
mvn test -Dtest="**/*IntegrationTest"
```

### Test Coverage

To generate a test coverage report with JaCoCo:

```bash
mvn clean verify
# Coverage report will be in: target/site/jacoco/index.html
```

**Current Test Coverage (JaCoCo Report):** 96%

**Test Structure:**
- **Unit Tests**: All Value Objects, Domain Services, Mappers, Use Cases
- **Integration Tests**: REST API endpoints with real database
- **Mock-based Tests**: Controllers, Exception Handlers, Repository implementations

### Integration Test Scenarios

The application includes 5 critical integration tests that validate the complete price selection flow:

**Test 1: Morning Query (June 14 at 10:00)**
- Request: Product 35455, Brand 1 (ZARA), Date: 2020-06-14T10:00:00
- Expected: Price list 1, Price: 35.50 EUR
- Validates: Only one price is active in the morning

**Test 2: Afternoon Query (June 14 at 16:00)**
- Request: Product 35455, Brand 1 (ZARA), Date: 2020-06-14T16:00:00
- Expected: Price list 2, Price: 25.45 EUR
- Validates: Higher priority price (priority 1) wins over price list 1 (priority 0)

**Test 3: Evening Query (June 14 at 21:00)**
- Request: Product 35455, Brand 1 (ZARA), Date: 2020-06-14T21:00:00
- Expected: Price list 1, Price: 35.50 EUR
- Validates: Returns to base price after promotional period ends

**Test 4: Morning Query (June 15 at 10:00)**
- Request: Product 35455, Brand 1 (ZARA), Date: 2020-06-15T10:00:00
- Expected: Price list 3, Price: 30.50 EUR
- Validates: New promotional price on second day

**Test 5: Evening Query (June 16 at 21:00)**
- Request: Product 35455, Brand 1 (ZARA), Date: 2020-06-16T21:00:00
- Expected: Price list 4, Price: 38.95 EUR
- Validates: Long-term promotional price takes effect

These tests ensure that the priority-based price selection algorithm works correctly across different time periods and overlapping prices.

---

## Database Access

### H2 Console

The application uses an **H2 in-memory database** with a web console for inspection during development.

**Access H2 Console:**
```
http://localhost:8080/h2-console
```

**Login Credentials:**

| Field | Value |
|-------|-------|
| **JDBC URL** | `jdbc:h2:mem:price-scheduler-db` |
| **Username** | `admin` |
| **Password** | *(leave empty)* |
| **Driver Class** | `org.h2.Driver` |

**Note:** These credentials are defined in `src/main/resources/application.yml`

### Test Data

The application automatically loads test data on startup through SQL scripts:

1. **Schema Creation** (`src/main/resources/schema.sql`)
   - Creates the `PRICES` table with all necessary columns and constraints

2. **Data Loading** (`src/main/resources/data.sql`)
   - Loads 4 test price records for product 35455 and brand 1 (ZARA)

**Configuration:**

This is achieved through Spring Boot's SQL initialization feature configured in `application.yml`:

```yaml
spring:
  sql:
    init:
      mode: always                          # Always execute scripts on startup
      schema-locations: classpath:schema.sql # DDL script
      data-locations: classpath:data.sql     # DML script
  jpa:
    defer-datasource-initialization: true    # Execute scripts before Hibernate validation
```

**Loaded Test Data:**

| Price List | Product | Brand | Start Date | End Date | Priority | Price | Currency |
|------------|---------|-------|------------|----------|----------|-------|----------|
| 1 | 35455 | 1 | 2020-06-14 00:00:00 | 2020-12-31 23:59:59 | 0 | 35.50 | EUR |
| 2 | 35455 | 1 | 2020-06-14 15:00:00 | 2020-06-14 18:30:00 | 1 | 25.45 | EUR |
| 3 | 35455 | 1 | 2020-06-15 00:00:00 | 2020-06-15 11:00:00 | 1 | 30.50 | EUR |
| 4 | 35455 | 1 | 2020-06-15 16:00:00 | 2020-12-31 23:59:59 | 1 | 38.95 | EUR |

You can query this data directly in the H2 console:

```sql
SELECT * FROM PRICES ORDER BY START_DATE, PRIORITY DESC;
```

---

## Monitoring and Observability

### Spring Boot Actuator

The application includes **Spring Boot Actuator** for production-ready monitoring and management endpoints.

**Base URL:**
```
http://localhost:8080/actuator
```

### Available Endpoints

#### Core Endpoints

| Endpoint | Description | Example |
|----------|-------------|---------|
| `/actuator` | List all available endpoints | Shows discovery document |
| `/actuator/health` | Application health status | Database connectivity, disk space |
| `/actuator/info` | Application information | Version, description, metadata |

#### Metrics and Monitoring

| Endpoint | Description |
|----------|-------------|
| `/actuator/metrics` | List of available metrics (JVM, HTTP, custom) |
| `/actuator/metrics/jvm.memory.used` | Current JVM memory usage |
| `/actuator/metrics/http.server.requests` | HTTP request statistics |
| `/actuator/metrics/system.cpu.usage` | CPU usage percentage |

#### Application Introspection

| Endpoint | Description |
|----------|-------------|
| `/actuator/beans` | List of all Spring beans in the application context |
| `/actuator/mappings` | All REST endpoint mappings (controllers) |
| `/actuator/env` | Environment properties and configuration |
| `/actuator/configprops` | All `@ConfigurationProperties` beans |
| `/actuator/loggers` | Logger configuration (can be changed at runtime) |

#### Diagnostics

| Endpoint | Description | Content-Type |
|----------|-------------|--------------|
| `/actuator/threaddump` | JVM thread dump for debugging | `text/plain` |
| `/actuator/heapdump` | JVM heap dump (downloads .hprof file) | `application/octet-stream` |

**Configuration:**

All actuator endpoints are exposed via configuration in `application.yml`:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"              # Expose all endpoints
  endpoint:
    health:
      show-details: "always"      # Show detailed health information
```

**Security Note:** In production, consider securing actuator endpoints with Spring Security and exposing only necessary endpoints.

---

## Development Tools

### Recommended Tools

**API Testing:**
- **Postman** - Feature-rich API testing tool
- **cURL** - Command-line HTTP client
- **Swagger UI** - Built-in interactive API documentation

**Database Inspection:**
- **H2 Console** - Built-in web console (http://localhost:8080/h2-console)
- **IntelliJ Database Tools** - Built-in database client

**Code Quality:**
- **SonarLint** - IDE plugin for code quality analysis
- **JaCoCo** - Code coverage reporting

### Testing with Postman

**Ready-to-Use Postman Collection:**

A pre-configured Postman collection is available in the project root for immediate testing:

📁 **[202510191903-prices-scheduler-api.postman_collection.json](202510191903-prices-scheduler-api.postman_collection.json)**

This collection includes:
- ✅ **5 integration test scenarios** documented in the Testing section
- ✅ **Additional validation tests** for error cases (404, 400)
- ✅ **Edge case tests** to verify proper error handling

**Import the Collection:**

1. Start the application
2. Open Postman
3. Click **Import** → **File**
4. Select `202510191903-prices-scheduler-api.postman_collection.json` from the project root
5. The collection will be ready to use immediately

**Alternative: Import from OpenAPI:**

You can also import the API specification directly:
1. Click **Import** → **Link**
2. Paste: `http://localhost:8080/v3/api-docs`
3. Click **Import**

**Test Scenarios Included:**

The collection includes all 5 integration tests:

```
Price Scheduler API Tests
├── Test 1: June 14 at 10:00 (Price: 35.50 EUR)
├── Test 2: June 14 at 16:00 (Price: 25.45 EUR - Higher priority)
├── Test 3: June 14 at 21:00 (Price: 35.50 EUR)
├── Test 4: June 15 at 10:00 (Price: 30.50 EUR)
├── Test 5: June 16 at 21:00 (Price: 38.95 EUR)
├── Test 6: Not Found (404) - Product doesn't exist
└── Test 7: Validation Error (400) - Invalid parameters
```

---

## Architecture

### Domain-Driven Design (DDD)

This project implements **Domain-Driven Design (DDD)** tactical patterns to achieve a clean, maintainable, and testable architecture. The codebase is organized into distinct layers, each with specific responsibilities.

**Key DDD Concepts Applied:**
- **Value Objects** - Immutable, self-validating domain primitives
- **Entities** - Objects with identity and lifecycle
- **Aggregates** - Cluster of entities and value objects with a root
- **Repositories** - Abstraction for data access
- **Domain Services** - Business logic that doesn't belong to entities
- **Application Services (Use Cases)** - Orchestration of domain logic

### Project Structure

```
src/main/java/com/inditex/priceschedulerapi/
├── domain/                          # Domain Layer (Core Business Logic)
│   ├── model/                       # Entities and Aggregates
│   │   └── Price.java              # Price aggregate root
│   ├── valueobject/                # Value Objects
│   │   ├── BrandId.java
│   │   ├── ProductId.java
│   │   ├── PriceList.java
│   │   ├── Priority.java
│   │   ├── Money.java
│   │   └── DateRange.java
│   ├── repository/                 # Repository interfaces
│   │   └── PriceRepository.java
│   └── service/                    # Domain services
│       └── PriceSelectionService.java
│
├── application/                    # Application Layer (Use Cases)
│   ├── dto/                        # Data Transfer Objects
│   │   ├── PriceQueryRequest.java
│   │   └── PriceQueryResponse.java
│   ├── mapper/                     # DTO ↔ Domain mapping
│   │   └── PriceMapper.java
│   └── usecase/                    # Application services
│       └── GetApplicablePriceUseCase.java
│
├── infrastructure/                 # Infrastructure Layer (Technical Concerns)
│   ├── config/                     # Configuration
│   │   ├── OpenApiConfig.java
│   │   └── DataInitializerVerifier.java
│   └── persistence/                # Data persistence
│       ├── entity/
│       │   └── PriceEntity.java
│       ├── mapper/
│       │   └── PriceEntityMapper.java
│       └── repository/
│           ├── JpaPriceRepositoryAdapter.java
│           └── PriceRepositoryImpl.java
│
└── presentation/                   # Presentation Layer (API)
    ├── controller/
    │   └── PriceController.java
    └── exception/
        └── GlobalExceptionHandler.java
```

#### Domain Layer

The **heart of the application** containing business rules and domain logic. This layer is:
- **Framework-agnostic** - No Spring or JPA annotations
- **Fully testable** - Pure Java with business logic
- **Isolated** - No dependencies on other layers

**Key Components:**
- **Value Objects**: Immutable objects representing domain concepts (Money, DateRange, Priority, etc.)
- **Price Aggregate**: The root entity containing the business logic for price applicability
- **PriceRepository Interface**: Domain contract for data access (Dependency Inversion Principle)
- **PriceSelectionService**: Domain service implementing priority-based price selection

#### Application Layer

Orchestrates the flow of data between the domain and presentation layers. Implements **Use Case pattern**.

**Responsibilities:**
- Convert DTOs to domain objects
- Execute domain services
- Convert domain objects back to DTOs
- Transaction boundaries (via `@Service`)

**Key Components:**
- **GetApplicablePriceUseCase**: Orchestrates the price query flow
- **PriceQueryRequest/Response**: Java 21 records for API communication
- **PriceMapper**: Converts between DTOs and domain objects

#### Infrastructure Layer

Provides technical implementations for domain contracts and framework configurations.

**Responsibilities:**
- Database access (JPA/Hibernate)
- External service integrations
- Framework configuration (OpenAPI, etc.)
- Data initialization

**Key Components:**
- **PriceEntity**: JPA entity for database mapping
- **PriceEntityMapper**: Converts between domain and persistence objects
- **PriceRepositoryImpl**: Implements the domain repository interface
- **JpaPriceRepositoryAdapter**: Spring Data JPA repository
- **OpenApiConfig**: Swagger documentation configuration

#### Presentation Layer

Exposes the API to external consumers via REST endpoints.

**Responsibilities:**
- HTTP request/response handling
- Input validation
- Exception handling
- API documentation

**Key Components:**
- **PriceController**: REST controller with OpenAPI annotations
- **GlobalExceptionHandler**: Centralized exception handling with `@RestControllerAdvice`

### SOLID Principles

This project strictly adheres to **SOLID principles**:

1. **Single Responsibility Principle (SRP)**
   - Each class has one reason to change
   - Value Objects only validate and encapsulate data
   - Controllers only handle HTTP concerns
   - Services only contain business logic

2. **Open/Closed Principle (OCP)**
   - Domain interfaces allow extension without modification
   - Repository pattern enables swapping implementations

3. **Liskov Substitution Principle (LSP)**
   - All implementations honor their interface contracts
   - Value Objects are immutable and always valid

4. **Interface Segregation Principle (ISP)**
   - Small, focused interfaces (PriceRepository)
   - Clients depend only on what they need

5. **Dependency Inversion Principle (DIP)**
   - Domain layer defines interfaces (PriceRepository)
   - Infrastructure layer implements them (PriceRepositoryImpl)
   - High-level modules don't depend on low-level modules

---

## Technical Details

### Value Objects

Value Objects are **immutable, self-validating** domain primitives that encapsulate business rules:

**Characteristics:**
- Immutable (all fields are `final`)
- Private constructors
- Factory methods (`.of()`) for creation
- Built-in validation
- No identity (equality by value)

**Examples:**

```java
// Money with currency validation
Money price = Money.of(new BigDecimal("35.50"), "EUR");

// DateRange with validation (start must be before end)
DateRange dateRange = DateRange.of(startDate, endDate);

// Priority (comparable for selection logic)
Priority priority = Priority.of(1);
```

**Benefits:**
- Type safety (cannot pass a `ProductId` where `BrandId` is expected)
- Encapsulation of validation logic
- Explicit domain language
- Prevents primitive obsession anti-pattern

### Aggregate Root

**Price** is the aggregate root that encapsulates the business logic:

```java
public class Price {
    private final ProductId productId;
    private final BrandId brandId;
    private final PriceList priceList;
    private final DateRange dateRange;
    private final Priority priority;
    private final Money price;

    // Business logic method
    public boolean isApplicableOn(LocalDateTime applicationDate) {
        return dateRange.contains(applicationDate);
    }
}
```

**Benefits:**
- Enforces invariants
- Encapsulates business rules
- Clear transaction boundaries

### Repository Pattern

The repository pattern abstracts data access and enables Dependency Inversion Principle:

**Domain Interface** (domain/repository/PriceRepository.java):
```java
public interface PriceRepository {
    List<Price> findApplicablePrices(ProductId productId, BrandId brandId, LocalDateTime applicationDate);
}
```

**Infrastructure Implementation** (infrastructure/persistence/repository/PriceRepositoryImpl.java):
```java
@Component
public class PriceRepositoryImpl implements PriceRepository {
    // Uses JPA and mapper to implement domain contract
}
```

**Benefits:**
- Domain layer independent of persistence technology
- Easy to test with mock implementations
- Can swap database without changing domain code

### Data Initialization

Data is automatically loaded on application startup through a multi-step process:

1. **Spring Boot starts** and initializes the H2 database
2. **schema.sql executes** creating the PRICES table structure
3. **data.sql executes** inserting the 4 test records
4. **Hibernate validates** entity mappings against the existing schema
5. **DataInitializerVerifier runs** as a `CommandLineRunner` to log loaded data

This approach provides:
- **Explicit control** over database schema (no auto-DDL)
- **Version control** of database structure
- **Production-like environment** simulation
- **Easy migration** to real databases (PostgreSQL, MySQL, etc.)

---

## Contributing

This is a technical assessment project. For any questions or issues, please contact the repository maintainer.

## License

This project is provided as-is for evaluation purposes.

---

**Thank you for your interest in my work.**
