# persistence-nosql

> A Spring Boot module that implements a product catalog service using MongoDB, Spring Data MongoDB, and Mongock for database migrations.

## Documentation
* [SQL and NoSQL Architectural Comparison](../docs/sql_and_nosql_architectural_comparison.md)

## Tech Stack
*   Java 21
*   Spring Boot 3.5.11
*   Gradle 9.x.x
*   MongoDB (Driver managed by Spring Boot)
*   Mongock (Database Migrations)
*   Testcontainers 1.20.4

## Prerequisites
*   Java 21
*   Docker 24.0+ (Required for integration tests via Testcontainers)

## Getting Started

### Build
```bash
./gradlew :persistence-nosql:build
```
### Run
```bash
./gradlew :persistence-nosql:bootRun
```

## Environment Variables / Configuration
| Variable | Description | Default |
| :--- | :--- | :--- |
| `spring.data.mongodb.database` | MongoDB database name | `appdb` |
| `spring.data.mongodb.uri` | Database connection string | `mongodb://localhost:27017/appdb` |

## API Endpoints
| Verb | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/nosql/products` | Searches and filters products by name, category, price, stock levels, and dynamic attributes with pagination. |

## Testing

| Name | Type | Description |
| :--- | :--- | :--- |
| `ProductControllerIntegrationTest` | Integration | Verifies product search and dynamic attribute filtering logic via REST API endpoints. |
| `ProductRepositoryTest` | Integration | Validates custom MongoTemplate queries and data access logic. |
| `MongockSetupTest` | Integration | Confirms that database migrations and indexes are correctly applied. |

## Constraints
*   Requires a running Docker daemon to execute the integration test suite (Testcontainers).
