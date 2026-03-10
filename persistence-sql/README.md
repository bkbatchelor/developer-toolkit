# persistence-sql

> A Spring Boot module that implements a product catalog service using PostgreSQL, Spring Data JPA, and Liquibase for database migrations.

## Documentation
* [SQL and NoSQL Architectural Comparison](../docs/sql_and_nosql_architectural_comparison.md)

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

| Name of Method | Name of Class | Type | Description |
| :--- | :--- | :--- | :--- |
| `testPostgresPropertiesAreConfigured` | `DatabasePropertiesTest` | Integration | Tests if PostgreSQL properties are correctly configured. |
| `testLiquibaseBeanIsConfigured` | `LiquibaseSetupTest` | Integration | Confirms that Liquibase bean is correctly configured. |
| `shouldGetAllProducts` | `ProductControllerIntegrationTest` | Integration | Verifies retrieving all products via REST API. |
| `shouldSearchProductsByName` | `ProductControllerIntegrationTest` | Integration | Verifies product search by name via REST API. |
| `shouldFilterProductsByPriceRange` | `ProductControllerIntegrationTest` | Integration | Verifies product filtering by price range via REST API. |
| `testCategoryPersistence` | `CategoryTest` | Integration | Validates Category entity persistence. |
| `testProductPersistence` | `ProductTest` | Integration | Validates Product entity persistence. |
| `testSupplierPersistence` | `SupplierTest` | Integration | Validates Supplier entity persistence. |
| `shouldFindProductsByNameContaining` | `ProductRepositoryTest` | Integration | Validates finding products by name containing via JPA specifications. |
| `shouldFindProductsByCategory` | `ProductRepositoryTest` | Integration | Validates finding products by category via JPA specifications. |
| `shouldFindProductsByPriceRange` | `ProductRepositoryTest` | Integration | Validates finding products by price range via JPA specifications. |
| `shouldFindProductsByMinStock` | `ProductRepositoryTest` | Integration | Validates finding products by minimum stock via JPA specifications. |
| `shouldFindProductsByCombinedCriteria` | `ProductRepositoryTest` | Integration | Validates finding products by combined criteria via JPA specifications. |
| `shouldBuildProductResponseDTO` | `ProductResponseDTOTest` | Unit | Tests DTO builder for product response. |

## Constraints
*   Requires a running Docker daemon to execute the integration test suite (Testcontainers).
