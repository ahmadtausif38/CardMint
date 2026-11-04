# CardMint – Fintech Microservices Platform

CardMint is a Java-based fintech backend application designed to simulate a modern prepaid card management ecosystem using **Spring Boot Microservices**.

The platform focuses on customer identity management, account creation, card issuance, card lifecycle operations, and integration with an external card processor.

The project aims to demonstrate real-world backend engineering concepts, including secure authentication, REST API development, service communication, event-driven architecture, and reliable transaction processing.

## Architecture Overview

CardMint follows a modular microservices architecture, where each service owns a specific business responsibility.

```text
                         ┌──────────────────────┐
                         │       CardMint       │
                         │   Fintech Platform   │
                         └──────────┬───────────┘
                                    │
              ┌─────────────────────┼─────────────────────┐
              │                     │                     │
              ▼                     ▼                     ▼
     ┌─────────────────┐   ┌─────────────────┐   ┌─────────────────┐
     │ Identity Service│   │ Account Service │   │ Card Issue      │
     │                 │   │                 │   │ Service         │
     └─────────────────┘   └─────────────────┘   └────────┬────────┘
                                                          │
                                                          ▼
                                                ┌──────────────────┐
                                                │ Card Processor   │
                                                │ Integration      │
                                                └──────────────────┘
```

## Microservices

| Service | Responsibility |
|---|---|
| **Identity Service** | Customer registration, authentication, password hashing, and JWT-based security. |
| **Account Service** | Customer account creation and account lifecycle management. |
| **Card Issue Service** | Card issuance, inventory validation, and card lifecycle management. |
| **Card Processor** | Simulates external processor integration and card-related operations. |
| **Platform Parent** | Centralized Maven parent configuration and dependency management. |

## Technology Stack

| Category | Technologies |
|---|---|
| Programming Language | Java 17 |
| Backend Framework | Spring Boot |
| Security | Spring Security, JWT |
| API Development | REST APIs |
| Database | PostgreSQL |
| ORM | Spring Data JPA, Hibernate |
| Messaging | Apache Kafka |
| Build Tool | Maven |
| Testing | JUnit 5, Mockito |
| Version Control | Git, GitHub |

## Key Functional Areas

### Identity Management
- Customer registration and identity management.
- Secure password hashing.
- JWT-based authentication and authorization.
- Secured API endpoints.

### Account Management
- Customer account creation and management.
- User-to-account association.
- Account lifecycle management.

### Card Management
- Card inventory validation using barcode information.
- Card issuance against eligible customer accounts.
- Association of cards with customers and accounts.
- Card lifecycle operations, including activation and blocking.

### Processor Integration
- Integration with a simulated external card processor.
- Card issuance and status management operations.
- Separation of internal business logic from processor-specific operations.

## Project Structure

```text
CardMint/
│
├── identity-service/
│
├── cardmint-account-service/
│
├── cardmint-cardissue/
│
├── card-processor/
│
├── cardmint-platform-parent/
│
└── README.md
```

## Getting Started

### Prerequisites

Make sure the following tools are installed:

- Java 17 or later
- Apache Maven
- PostgreSQL
- Git
- IntelliJ IDEA (recommended)

### Clone the Repository

```bash
git clone https://github.com/ahmadtausif38/CardMint.git

cd CardMint
```

### Database Configuration

Create the required PostgreSQL databases and configure the datasource for each service in its `application.yml` or `application.properties`.

Example configuration:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cardmint
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

Use environment variables for database credentials. Never commit passwords, API secrets, or private keys to GitHub.

### Build and Run

Navigate to the relevant service directory and execute:

```bash
mvn clean install
```

Start the Spring Boot application:

```bash
mvn spring-boot:run
```

Configure the required database and service dependencies before starting each application.

## Engineering Focus

CardMint is designed around practical backend engineering principles:

- Modular microservices architecture.
- Secure authentication and authorization.
- RESTful API design.
- Separation of business logic and integration layers.
- Centralized exception handling and validation.
- Event-driven communication.
- Reliable card and account lifecycle management.

## Project Objective

The goal of CardMint is to build a realistic fintech backend system while gaining hands-on experience with Java, Spring Boot, microservices, distributed systems, and financial application workflows.

---

**CardMint** | Built with Java 17 & Spring Boot
