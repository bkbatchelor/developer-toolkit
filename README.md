# Developer Toolkit

> The Developer Toolkit is a monorepo designed to provide a comparative analysis of SQL (PostgreSQL) and NoSQL (MongoDB) database architectures within a modern Java and Spring Boot ecosystem. It serves as a practical guide and boilerplate for understanding the trade-offs and benefits of each approach.

## Documentation
* [SQL and NoSQL Architectural Comparison](docs/sql_and_nosql_architectural_comparison.md)

## Tech Stack
*   **Java** 21
*   **Spring Boot** 3.5.11
*   **Gradle** 9.x.x
*   **PostgreSQL** 42.7.5
*   **MongoDB** (Driver managed by Spring Boot)
*   **Liquibase** (Database Migrations)
*   **Spring Data JPA / Spring Data MongoDB**
*   **Testcontainers** 1.20.4
*   **Lombok** 1.18.36

## Prerequisites
*   **Java 21**
*   **Docker 24.0+** (Required for integration tests via Testcontainers)

## Getting Started

### Build
```bash
./gradlew build
```

### Run (SQL Module)
```bash
./gradlew :persistence-sql:bootRun
```

### Run (NoSQL Module)
```bash
./gradlew :persistence-nosql:bootRun
```

## Environment Variables / Configuration

### persistence-sql
| Variable | Description | Default |
| :--- | :--- | :--- |
| `DB_USERNAME` | PostgreSQL username | `user` |
| `DB_PASSWORD` | PostgreSQL password | `password` |
| `spring.datasource.url` | Database connection string | `jdbc:postgresql://localhost:5432/dt_sql` |

## API Endpoints

### persistence-sql
| Verb | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/sql/products` | Searches and filters products by name, category, price, and stock levels. |

## Testing

The project uses **Testcontainers** to spin up actual database instances for integration testing.

### persistence-sql
| Name | Type | Description |
| :--- | :--- | :--- |
| `ProductControllerIntegrationTest` | Integration | Verifies product search and filtering logic via REST API endpoints. |
| `ProductRepositoryTest` | Integration | Validates custom JPA repository queries and data access logic. |
| `LiquibaseSetupTest` | Integration | Confirms that database migrations are correctly applied to the schema. |

## Constraints
*   Requires **Java 21**.
*   **Docker** must be running to execute the full test suite.
*   The `persistence-nosql` module is currently under development.
