# persistence-sql

> A Spring Boot module that implements a product catalog service using PostgreSQL, Spring Data JPA, and Liquibase for database migrations.

## Tech Stack
*   Java 21
*   Spring Boot 3.5.11
*   Gradle 9.x.x
*   PostgreSQL 42.7.5
*   Liquibase Core
*   Testcontainers 1.20.4
*   Lombok 1.18.36

## Prerequisites
*   Java 21
*   Docker 24.0+ (Required for integration tests via Testcontainers)

## Getting Started

### Build
```bash
./gradlew :persistence-sql:build
```
### Run
```bash
./gradlew :persistence-sql:bootRun
```

## Environment Variables / Configuration
| Variable | Description | Default |
| :--- | :--- | :--- |
| `DB_USERNAME` | PostgreSQL username | `user` |
| `DB_PASSWORD` | PostgreSQL password | `password` |
| `spring.datasource.url` | Database connection string | `jdbc:postgresql://localhost:5432/dt_sql` |

## API Endpoints
| Verb | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/sql/products` | Searches and filters products by name, category, price, and stock levels. |

## Testing

| Name | Type | Description |
| :--- | :--- | :--- |
| `ProductControllerIntegrationTest` | Integration | Verifies product search and filtering logic via REST API endpoints. |
| `ProductRepositoryTest` | Integration | Validates custom JPA repository queries and data access logic. |
| `LiquibaseSetupTest` | Integration | Confirms that database migrations are correctly applied to the schema. |

## Constraints
*   Requires a running Docker daemon to execute the integration test suite (Testcontainers).
